package com.hoop.api.controller;

import com.hoop.api.config.UserPrincipal;
import com.hoop.api.domain.User;
import com.hoop.api.request.game.CommentCreate;
import com.hoop.api.service.game.CommentService;
import com.hoop.api.service.user.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    private final ProfileService profileService;
    @PostMapping("/game/{gameId}/comments")
    public void write(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long gameId, @RequestBody @Valid CommentCreate request) {;
        if (request.getAuthor() == null ) request.setAuthor(profileService.get(userPrincipal.getUserId()).getNickName());
        commentService.write(gameId, request);
    }

//    @PostMapping("/comments/{commentId}/delete")
//    public void delete(@PathVariable Long commentId, @RequestBody @Valid CommentDelete request) {
//        postCommentService.delete(commentId, request);
//    }
}
