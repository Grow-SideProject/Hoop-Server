package com.hoop.api.exception;

public class AlreadyExistsUserException extends HoopException {

    private static final String MESSAGE = "이미 게임에 참가 중입니다.";

    public AlreadyExistsUserException() {
        super(MESSAGE);
    }
    public AlreadyExistsUserException(String message) {
        super(message);
    }

    @Override
    public Integer getStatusCode() {
        return 400;
    }
}
