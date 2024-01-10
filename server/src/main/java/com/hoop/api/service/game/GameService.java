package com.hoop.api.service.game;

import com.hoop.api.constant.AttendantStatus;
import com.hoop.api.domain.Game;

import com.hoop.api.domain.GameAttendant;
import com.hoop.api.domain.User;
import com.hoop.api.exception.AlreadyExistsGameAttendException;
import com.hoop.api.exception.GameNotFound;
import com.hoop.api.exception.UserNotFound;
import com.hoop.api.repository.GameAttendantRepository;
import com.hoop.api.repository.game.GameRepository;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.game.GameCreate;
import com.hoop.api.request.game.GameSearch;
import com.hoop.api.response.GameAttendantResponse;
import com.hoop.api.response.GameResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final GameAttendantRepository gameAttendantRepository;
    private final UserRepository userRepository;

    public GameResponse get(Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFound::new);
        return GameResponse.builder()
                .id(game.getId())
                .title(game.getTitle())
                .content(game.getContent())
                .courtName(game.getCourtName())
                .address(game.getAddress())
                .gameCategory(game.getGameCategory())
                .startTime(game.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .duration(game.getDuration())
                .gender(game.getGender())
                .maxAttend(game.getMaxAttend())
                .isBallFlag(game.getIsBallFlag())
                .createdAt(game.getCreatedAt())
                .levels(game.getLevels())
                .comments(game.getComments())
                .gameAttendants(game.getGameAttendants())
                .build();
    }

    public List<GameResponse> getList(GameSearch gameSearch) {
        return gameRepository.getList(gameSearch).stream()
                .map(GameResponse::new)
                .collect(Collectors.toList());
    }

//    public Page<GameResponse> getPage(Pageable pageable) {
//        return gameRepository.findAll(pageable)
//                .map(GameResponse::new);
//    }

    @Transactional
    public void create(Long userId, GameCreate gameCreate) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        Game game = gameCreate.toGame();
        GameAttendant gameAttendant = GameAttendant
                .builder()
                .game(game)
                .user(user)
                .isHost(true)
                .status(AttendantStatus.APPROVE)
                .isBallFlag(gameCreate.getIsBallFlag())
                .build();
        gameRepository.save(game);
        gameAttendantRepository.save(gameAttendant);
    }

    public GameAttendantResponse attendGame(Long userId, Long gameId, boolean ballFlag) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFound::new);
        gameAttendantRepository.findByUserAndGame(user, game)
                .ifPresent(gameAttendant -> {
                    throw new AlreadyExistsGameAttendException();
        });
        GameAttendant gameAttendant = GameAttendant
                .builder()
                .user(user)
                .game(game)
                .status(AttendantStatus.DEFAULT)
                .isHost(false)
                .isBallFlag(ballFlag)
                .build();
        gameAttendantRepository.save(gameAttendant);
        return new GameAttendantResponse(gameAttendant);
    }

    @Transactional
    public GameAttendantResponse exitGame(Long userId, Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFound::new);
        GameAttendant gameAttendant = gameAttendantRepository.findByUserIdAndGameId(userId, gameId)
                    .orElseThrow(GameNotFound::new);
        for (GameAttendant attendant : game.getGameAttendants()) {
            if (!attendant.getIsHost() && attendant.getStatus().equals(AttendantStatus.APPROVE) ) {
                attendant.setHost(true);
                gameAttendantRepository.save(attendant);
                break;
            }
        }
        gameAttendant.setHost(false);
        gameAttendant.setAttend(AttendantStatus.EXIT);
        gameAttendantRepository.save(gameAttendant);
        return new GameAttendantResponse(gameAttendant);
    }

    @Transactional
    public GameAttendantResponse approveGame(Long hostId, Long gameId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFound::new);
        GameAttendant gameAttendant = gameAttendantRepository.findByUserAndGame(user, game).orElseThrow(GameNotFound::new);
        gameAttendant.setAttend(AttendantStatus.APPROVE);
        gameAttendantRepository.save(gameAttendant);
        return new GameAttendantResponse(gameAttendant);
    }
    @Transactional
    public GameAttendantResponse rejectGame(Long hostId, Long gameId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFound::new);
        GameAttendant gameAttendant = gameAttendantRepository.findByUserAndGame(user, game).orElseThrow(GameNotFound::new);
        gameAttendant.setAttend(AttendantStatus.REJECT);
        gameAttendantRepository.save(gameAttendant);
        return new GameAttendantResponse(gameAttendant);
    }


    @Transactional
    public void removeGameAttend(Long userId, Long gameId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFound::new);
        GameAttendant gameAttendant = gameAttendantRepository.findByUserAndGame(user, game)
                .orElseThrow(GameNotFound::new);
        gameAttendantRepository.delete(gameAttendant);
    }
}
