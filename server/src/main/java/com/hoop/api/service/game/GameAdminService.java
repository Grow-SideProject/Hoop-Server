package com.hoop.api.service.game;

import com.hoop.api.constant.AttendantStatus;
import com.hoop.api.domain.*;
import com.hoop.api.exception.GameNotFound;
import com.hoop.api.exception.PostNotFound;
import com.hoop.api.exception.UserNotFound;
import com.hoop.api.repository.AttendantRepository;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.repository.game.GameRepository;
import com.hoop.api.request.game.GameCreate;
import com.hoop.api.request.game.GameEdit;
import com.hoop.api.response.AttendantResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class GameAdminService {
    private final GameRepository gameRepository;
    private final AttendantRepository attendantRepository;
    private final UserRepository userRepository;
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

    @Transactional
    public void edit(Long userId, GameEdit gameEdit) {
        Game game = gameRepository.findById(gameEdit.getGameId())
                .orElseThrow(GameNotFound::new);
        game.GameEdit(gameEdit);
        gameRepository.save(game);
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
}
