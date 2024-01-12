package com.hoop.api.service.game;
import com.hoop.api.domain.Comment;
import com.hoop.api.domain.Game;
import com.hoop.api.domain.User;
import com.hoop.api.exception.CommentNotFound;

import com.hoop.api.exception.UserNotFound;
import com.hoop.api.exception.GameNotFound;
import com.hoop.api.repository.CommentRepository;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.repository.game.GameRepository;
import com.hoop.api.request.game.CommentCreate;
import com.hoop.api.request.game.CommentDelete;
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
    public void create(Long userId, Long gameId, CommentCreate request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
        Game game = gameRepository.findById(gameId)
                .orElseThrow(GameNotFound::new);

        Comment comment = Comment.builder()
                .user(user)
                .content(request.getContent())
                .build();

        Comment parent = null;
        if(request.getParentId() != null) {
            parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(CommentNotFound::new);
        }

        comment.setParent(parent);
        comment.setGame(game);
        commentRepository.save(comment);
    }

    public void delete(Long commentId, CommentDelete request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFound::new);
        commentRepository.delete(comment);
    }
}
