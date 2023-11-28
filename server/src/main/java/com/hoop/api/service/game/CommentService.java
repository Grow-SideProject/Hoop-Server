package com.hoop.api.service.game;

import com.hoop.api.domain.Comment;
import com.hoop.api.domain.PostComment;
import com.hoop.api.domain.Game;
import com.hoop.api.exception.CommentNotFound;
import com.hoop.api.exception.InvalidPassword;
import com.hoop.api.exception.GameNotFound;
import com.hoop.api.repository.CommentRepository;
import com.hoop.api.repository.game.GameRepository;
import com.hoop.api.request.game.CommentCreate;
import com.hoop.api.request.game.CommentDelete;
import com.hoop.api.request.post.PostCommentCreate;
import com.hoop.api.request.post.PostCommentDelete;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final GameRepository gameRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void write(Long gameId, CommentCreate request) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(GameNotFound::new);

        Comment comment = Comment.builder()
                .author(request.getAuthor())
                .content(request.getContent())
                .build();
        comment.setGame(game);
        commentRepository.save(comment);
    }

    public void delete(Long commentId, CommentDelete request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFound::new);
        commentRepository.delete(comment);
    }
}
