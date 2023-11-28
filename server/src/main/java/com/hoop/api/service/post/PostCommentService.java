//package com.hoop.api.service.post;
//
//import com.hoop.api.domain.PostComment;
//import com.hoop.api.domain.Post;
//import com.hoop.api.exception.CommentNotFound;
//import com.hoop.api.exception.InvalidPassword;
//import com.hoop.api.exception.PostNotFound;
//import com.hoop.api.repository.CommentRepository;
//import com.hoop.api.repository.PostCommentRepository;
//import com.hoop.api.repository.post.PostRepository;
//import com.hoop.api.request.post.PostCommentCreate;
//import com.hoop.api.request.post.PostCommentDelete;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class PostCommentService {
//
//    private final PostRepository postRepository;
//    private final PostCommentRepository commentRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Transactional
//    public void write(Long postId, PostCommentCreate request) {
//        Post post = postRepository.findById(postId)
//                .orElseThrow(PostNotFound::new);
//
//        String encryptedPassword = passwordEncoder.encode(request.getPassword());
//
//        PostComment postComment = PostComment.builder()
//                .author(request.getAuthor())
//                .password(encryptedPassword)
//                .content(request.getContent())
//                .build();
//
//        post.addComment(postComment);
//    }
//
//    public void delete(Long commentId, PostCommentDelete request) {
//        PostComment postComment = commentRepository.findById(commentId)
//                .orElseThrow(CommentNotFound::new);
//
//        String encryptedPassword = postComment.getPassword();
//        if (!passwordEncoder.matches(request.getPassword(), encryptedPassword)) {
//            throw new InvalidPassword();
//        }
//
//        commentRepository.delete(postComment);
//    }
//}
