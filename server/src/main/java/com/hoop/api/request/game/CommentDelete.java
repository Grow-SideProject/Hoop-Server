package com.hoop.api.request.game;

import lombok.Data;

@Data
public class CommentDelete {

    long commentId;

    public CommentDelete() {

    }
    public CommentDelete(long commentId) {
        this.commentId = commentId;
    }
}
