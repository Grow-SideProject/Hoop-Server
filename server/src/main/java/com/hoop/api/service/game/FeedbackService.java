package com.hoop.api.service.game;

import com.hoop.api.domain.Feedback;
import com.hoop.api.domain.Game;
import com.hoop.api.domain.User;
import com.hoop.api.exception.GameNotFound;
import com.hoop.api.repository.FeedbackRepository;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.repository.game.GameRepository;
import com.hoop.api.request.user.FeedbackRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final GameRepository gameRepository;

    private final UserRepository userRepository;

    private final FeedbackRepository feedbackRepository;
    @Transactional
    public void create(Long userId, FeedbackRequest feedbackRequest) {
        Game game = gameRepository.findById(feedbackRequest.getGameId())
                .orElseThrow(GameNotFound::new);
        User user = userRepository.findById(userId)
                .orElseThrow(GameNotFound::new);
        User target = userRepository.findById(feedbackRequest.getTargetId())
                .orElseThrow(GameNotFound::new);
        Feedback feedback = Feedback.builder()
                .user(user)
                .target(target)
                .game(game)
                .content(feedbackRequest.getContent())
                .score(feedbackRequest.getScore())
                .build();
        feedbackRepository.save(feedback);
    }
}
