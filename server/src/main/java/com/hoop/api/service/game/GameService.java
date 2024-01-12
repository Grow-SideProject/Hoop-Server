package com.hoop.api.service.game;

import com.hoop.api.constant.AttendantStatus;
import com.hoop.api.domain.Attendant;
import com.hoop.api.domain.Game;

import com.hoop.api.domain.User;
import com.hoop.api.exception.AlreadyExistsGameAttendException;
import com.hoop.api.exception.GameNotFound;
import com.hoop.api.exception.UserNotFound;
import com.hoop.api.repository.AttendantRepository;
import com.hoop.api.repository.game.GameRepository;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.game.GameCreate;
import com.hoop.api.request.game.GameSearch;
import com.hoop.api.response.AttendantResponse;
import com.hoop.api.response.GameDetailResponse;
import com.hoop.api.response.GameListResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final AttendantRepository attendantRepository;
    private final UserRepository userRepository;

    public GameDetailResponse get(Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFound::new);
        return GameDetailResponse.builder()
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
                .attendants(game.getAttendants())
                .build();
    }

    public List<GameListResponse> getList(GameSearch gameSearch) {
        return gameRepository.getList(gameSearch).stream()
                .map(GameListResponse::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public void create(Long userId, GameCreate gameCreate) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        Game game = gameCreate.toGame();
        Attendant attendant = Attendant
                .builder()
                .game(game)
                .user(user)
                .isHost(true)
                .status(AttendantStatus.APPROVE)
                .isBallFlag(gameCreate.getIsBallFlag())
                .build();
        gameRepository.save(game);
        attendantRepository.save(attendant);
    }

    public AttendantResponse attendGame(Long userId, Long gameId, boolean ballFlag) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFound::new);
        attendantRepository.findByUserAndGame(user, game)
                .ifPresent(gameAttendant -> {
                    throw new AlreadyExistsGameAttendException();
        });
        Attendant attendant = Attendant
                .builder()
                .user(user)
                .game(game)
                .status(AttendantStatus.DEFAULT)
                .isHost(false)
                .isBallFlag(ballFlag)
                .build();
        attendantRepository.save(attendant);
        return new AttendantResponse(attendant);
    }

    @Transactional
    public AttendantResponse exitGame(Long userId, Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFound::new);
        Attendant gameAttendant = attendantRepository.findByUserIdAndGameId(userId, gameId)
                    .orElseThrow(GameNotFound::new);
        for (Attendant attendant : game.getAttendants()) {
            if (!attendant.getIsHost() && attendant.getStatus().equals(AttendantStatus.APPROVE) ) {
                attendant.setHost(true);
                attendantRepository.save(attendant);
                break;
            }
        }
        gameAttendant.setHost(false);
        gameAttendant.setAttend(AttendantStatus.EXIT);
        attendantRepository.save(gameAttendant);
        return new AttendantResponse(gameAttendant);
    }

    @Transactional
    public AttendantResponse approveGame(Long hostId, Long gameId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFound::new);
        Attendant attendant = attendantRepository.findByUserAndGame(user, game).orElseThrow(GameNotFound::new);
        attendant.setAttend(AttendantStatus.APPROVE);
        attendantRepository.save(attendant);
        return new AttendantResponse(attendant);
    }
    @Transactional
    public AttendantResponse rejectGame(Long hostId, Long gameId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFound::new);
        Attendant attendant = attendantRepository.findByUserAndGame(user, game).orElseThrow(GameNotFound::new);
        attendant.setAttend(AttendantStatus.REJECT);
        attendantRepository.save(attendant);
        return new AttendantResponse(attendant);
    }


    @Transactional
    public void removeGameAttend(Long userId, Long gameId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFound::new);
        Attendant attendant = attendantRepository.findByUserAndGame(user, game)
                .orElseThrow(GameNotFound::new);
        attendantRepository.delete(attendant);
    }
}
