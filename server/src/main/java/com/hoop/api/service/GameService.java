package com.hoop.api.service;

import com.hoop.api.domain.Game;

import com.hoop.api.domain.User;
import com.hoop.api.exception.MatchNotFound;
import com.hoop.api.exception.UserNotFound;
import com.hoop.api.repository.GameAttendRepository;
import com.hoop.api.repository.GameRepository;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.game.GameAttend;
import com.hoop.api.request.game.GameCreate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final GameAttendRepository gameAttendRepository;
    private final UserRepository userRepository;

    @Transactional
    public void create(Long userId, GameCreate gameCreate) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());
        Game game = gameCreate.toGame();
        com.hoop.api.domain.GameAttend gameAttend = com.hoop.api.domain.GameAttend
                .builder()
                .game(game)
                .user(user)
                .game(game)
                .isHost(true)
                .isBallFlag(gameCreate.getIsBallFlag())
                .build();
        game.addGameAttend(gameAttend);
        gameRepository.save(game);
        gameAttendRepository.save(gameAttend);
    }

    public void attendGame(Long userId, GameAttend gameAttendRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());
        Game game = gameRepository.findById(gameAttendRequest.getGame().getId()).orElseThrow(() -> new MatchNotFound());
        com.hoop.api.domain.GameAttend gameAttend = com.hoop.api.domain.GameAttend
                .builder()
                .user(user)
                .game(game)
                .isHost(gameAttendRequest.getIsHost())
                .isBallFlag(gameAttendRequest.getIsBallFlag())
                .build();
        game.addGameAttend(gameAttend);
        gameRepository.save(game);
        gameAttendRepository.save(gameAttend);
    }

    @Transactional
    public void exitGame(Long userId, Long gameId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound());
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new MatchNotFound());
        com.hoop.api.domain.GameAttend gameAttend = gameAttendRepository.deleteOneByUserAndGame(user, game)
                .orElseThrow(()-> new MatchNotFound());
        game.popGameAttend(gameAttend);
    }
}
