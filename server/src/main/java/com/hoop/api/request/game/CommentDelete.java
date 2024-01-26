package com.hoop.api.request.game;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentDelete {
    @NotNull(message = "댓글 아이디를 입력해주세요.")
    long commentId;

    public CommentDelete() {

    }
    public CommentDelete(long commentId) {
        this.commentId = commentId;
    }
}
