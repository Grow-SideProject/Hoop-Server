package com.hoop.api.exception;

/**
 * status -> 404
 */
public class CommentNotFound extends HoopException {

    private static final String MESSAGE = "존재하지 않는 댓글입니다.";

    public CommentNotFound() {
        super(MESSAGE);
    }

    @Override
    public Integer getStatusCode() {
        return 404;
    }
}
