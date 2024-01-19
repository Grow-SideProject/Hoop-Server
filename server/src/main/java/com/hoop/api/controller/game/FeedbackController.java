package com.hoop.api.controller.game;

import com.hoop.api.config.UserPrincipal;
import com.hoop.api.request.user.FeedbackRequest;
import com.hoop.api.response.DefaultResponse;
import com.hoop.api.service.game.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping()
    public DefaultResponse feedback(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody FeedbackRequest request) {
        feedbackService.create(userPrincipal.getUserId(), request);
        return new DefaultResponse();
    }
}
