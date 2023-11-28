package com.hoop.api.request.game;

import lombok.Data;

@Data
public class CommentDelete {

    private String password;

    public CommentDelete() {

    }
    public CommentDelete(String password) {
        this.password = password;
    }
}
