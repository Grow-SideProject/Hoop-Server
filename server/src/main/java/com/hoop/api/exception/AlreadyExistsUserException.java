package com.hoop.api.exception;

public class AlreadyExistsUserException extends HoopException {

    private static final String MESSAGE = "이미 가입된 사용자입니다.";

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
