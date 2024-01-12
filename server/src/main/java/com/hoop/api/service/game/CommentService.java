package com.hoop.api.service.game;
import com.hoop.api.domain.Comment;
import com.hoop.api.domain.Game;
import com.hoop.api.domain.User;
import com.hoop.api.exception.CommentLevelConfilct;
import com.hoop.api.exception.CommentNotFound;

import com.hoop.api.exception.UserNotFound;
import com.hoop.api.exception.GameNotFound;
import com.hoop.api.repository.CommentRepository;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.repository.game.GameRepository;
import com.hoop.api.request.game.CommentCreate;
import com.hoop.api.request.game.CommentDelete;
import com.hoop.api.request.game.CommentEdit;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final GameRepository gameRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;


    @Transactional
    public void create(Long userId, CommentCreate request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
        Game game = gameRepository.findById(request.getGameId())
                .orElseThrow(GameNotFound::new);

        Comment comment = Comment.builder()
                .user(user)
                .content(request.getContent())
                .build();

        Comment parent = null;
        if(request.getParentId() != null) {
            parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(CommentNotFound::new);
            if (parent.getParent() != null) {
                throw new CommentLevelConfilct();
            }
        }
        comment.setParent(parent);
        comment.setGame(game);
        commentRepository.save(comment);
    }

    public void edit(Long userId, CommentEdit request) {
        Comment comment = commentRepository.findById(request.getCommentId())
                .orElseThrow(CommentNotFound::new);
        comment.setContent(request.getContent());
        commentRepository.save(comment);
    }

    @Transactional
    public void delete(Long userId, CommentDelete request) {
        Comment comment = commentRepository.findById(request.getCommentId())
                .orElseThrow(CommentNotFound::new);
        commentRepository.deleteByParentId(comment.getId());
        commentRepository.delete(comment);
    }
}
