package com.hoop.api.response;

import com.hoop.api.domain.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommentResponse {

    private Long id;

    private String content;

    private String nickName;

    private Long userId;

    private String profileImagePath;

    private Long parentId;

    private LocalDateTime createdAt;

    private List<CommentResponse> children = new ArrayList<>();

    private Boolean isMine;
    private Boolean isHost;

    @Builder
    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.createdAt = comment.getCreatedAt();
        this.content = comment.getContent();
        this.nickName =comment.getUser().getNickName();
        this.userId = comment.getUser().getId();
        this.profileImagePath = comment.getUser().getProfileImagePath();
        if(comment.getParent() != null) {
            this.parentId = comment.getParent().getId();
        }
    }
}
