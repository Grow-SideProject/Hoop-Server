package com.hoop.api.repository.game;

import com.hoop.api.domain.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteByParentId(Long id);

}
