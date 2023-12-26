package com.hoop.api.service.game;

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
import com.hoop.api.response.GameResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        return new GameResponse(game);
    }

    public List<GameResponse> getList(GameSearch gameSearch) {
        return gameRepository.getList(gameSearch).stream()
                .map(GameResponse::new)
                .collect(Collectors.toList());
    }

    public Page<GameResponse> getPage(Pageable pageable) {
        return gameRepository.findAll(pageable)
                .map(GameResponse::new);
    }

    @Transactional
    public void create(Long userId, GameCreate gameCreate) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        Game game = gameCreate.toGame();
        GameAttendant gameAttendant = GameAttendant
                .builder()
                .game(game)
                .user(user)
                .isHost(true)
                .isAttend(true)
                .isBallFlag(gameCreate.getIsBallFlag())
                .build();
        gameRepository.save(game);
        gameAttendantRepository.save(gameAttendant);
    }

    public void attendGame(Long userId, Long gameId, boolean ballFlag) {
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
                .isHost(false)
                .isAttend(true)
                .isBallFlag(ballFlag)
                .build();
        gameAttendantRepository.save(gameAttendant);
    }

    @Transactional
    public void exitGame(Long userId, Long gameId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFound::new);
        GameAttendant gameAttendant = gameAttendantRepository.findByUserAndGame(user, game)
                    .orElseThrow(GameNotFound::new);
        if (gameAttendant.getHost()) {
            // TODO: host가 나갔을 때 처리
            // isAttend = true인 값 중에서 id가 가장 빠른 거 가져오기
        }
        gameAttendant.setAttend(false);
        gameAttendant.removeGameAttend();

    }

    @Transactional
    public void removeGameAttend(Long userId, Long gameId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFound::new);
        GameAttendant gameAttendant = gameAttendantRepository.deleteOneByUserAndGame(user, game)
                .orElseThrow(GameNotFound::new);
        gameAttendant.removeGameAttend();
    }


}
