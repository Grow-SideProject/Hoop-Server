package com.hoop.api.service;

import com.hoop.api.domain.Attendant;
import com.hoop.api.domain.Feedback;
import com.hoop.api.domain.User;
import com.hoop.api.exception.GameNotFound;
import com.hoop.api.repository.FeedbackRepository;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.repository.game.AttendantRepository;
import com.hoop.api.request.user.FeedbackRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final UserRepository userRepository;
    private final AttendantRepository attendantRepository;
    private final FeedbackRepository feedbackRepository;
    @Transactional
    public void create(Long userId, FeedbackRequest feedbackRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(GameNotFound::new);
        Attendant attendant = attendantRepository.findById(feedbackRequest.getAttendantId())
                .orElseThrow(GameNotFound::new);
        Feedback feedback = Feedback.builder()
                .user(user)
                .target(attendant)
                .content(feedbackRequest.getContent())
                .score(feedbackRequest.getScore())
                .build();
        feedbackRepository.save(feedback);
    }
}
