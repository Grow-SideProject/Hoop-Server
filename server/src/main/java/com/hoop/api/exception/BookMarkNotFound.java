package com.hoop.api.exception;

/**
 * status -> 404
 */
public class BookMarkNotFound extends HoopException {

    private static final String MESSAGE = "없는 좋아요입니다.";

    public BookMarkNotFound() {
        super(MESSAGE);
    }

    @Override
    public Integer getStatusCode() {
        return 404;
    }
}
