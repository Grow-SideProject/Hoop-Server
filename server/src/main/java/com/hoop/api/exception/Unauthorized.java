package com.hoop.api.exception;

/**
 * status -> 401
 */
public class Unauthorized extends HoopException {

    private static final String MESSAGE = "인증이 필요합니다.";

    public Unauthorized() {
        super(MESSAGE);
    }
    public Unauthorized(String message) {
        super(message);
    }

    @Override
    public Integer getStatusCode() {
        return 401;
    }
}
