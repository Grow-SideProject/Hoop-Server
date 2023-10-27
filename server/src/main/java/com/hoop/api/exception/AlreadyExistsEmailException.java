package com.hoop.api.exception;

public class AlreadyExistsEmailException extends HoopException {

    private static final String MESSAGE = "이미 가입된 사용자입니다.";

    public AlreadyExistsEmailException() {
        super(MESSAGE);
    }

    @Override
    public Integer getStatusCode() {
        return 400;
    }
}
