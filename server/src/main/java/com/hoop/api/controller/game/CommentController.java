package com.hoop.api.controller.game;


import com.hoop.api.config.UserPrincipal;
import com.hoop.api.request.game.*;
import com.hoop.api.response.DefaultResponse;
import com.hoop.api.service.game.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/game/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping()
    public DefaultResponse create(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid CommentCreate commentCreate) {;
        commentService.create(userPrincipal.getUserId(), commentCreate);
        return new DefaultResponse();
    }

    @PatchMapping()
    public DefaultResponse edit(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid CommentEdit commentEdit) {;
        commentService.edit(userPrincipal.getUserId(),commentEdit);
        return new DefaultResponse();
    }

    @DeleteMapping()
    public DefaultResponse delete(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid CommentDelete commentDelete) {;
        commentService.delete(userPrincipal.getUserId(),commentDelete);
        return new DefaultResponse();
    }
}
