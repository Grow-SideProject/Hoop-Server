package com.hoop.api.controller.game;


import com.hoop.api.config.UserPrincipal;
import com.hoop.api.domain.Comment;
import com.hoop.api.domain.Game;
import com.hoop.api.request.game.*;
import com.hoop.api.response.CommentResponse;
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
    public CommentResponse create(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid CommentCreate commentCreate) {;
        Comment comment = commentService.create(userPrincipal.getUserId(), commentCreate);
        CommentResponse commentResponse = new CommentResponse(comment);
        Long hostId = comment.getGame().getAttendants().stream().filter(attendant -> attendant.getIsHost()).findFirst().get().getUser().getId();
        commentResponse.setIsHost(hostId.equals(userPrincipal.getUserId()));
        commentResponse.setIsMine(true);  // 내가 쓴 댓글이니까 true
        return commentResponse;
    }

    @PatchMapping()
    public CommentResponse edit(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid CommentEdit commentEdit) {;
        Comment comment = commentService.edit(userPrincipal.getUserId(),commentEdit);
        CommentResponse commentResponse = new CommentResponse(comment);
        Long hostId = comment.getGame().getAttendants().stream().filter(attendant -> attendant.getIsHost()).findFirst().get().getUser().getId();
        commentResponse.setIsHost(hostId.equals(userPrincipal.getUserId()));
        commentResponse.setIsMine(true);  // 내가 쓴 댓글이니까 true
        return commentResponse;
    }

    @DeleteMapping()
    public DefaultResponse delete(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid CommentDelete commentDelete) {;
        commentService.delete(userPrincipal.getUserId(),commentDelete);
        return new DefaultResponse();
    }
}
