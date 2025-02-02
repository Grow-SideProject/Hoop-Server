package com.hoop.api.exception;

public class InvalidPassword extends HoopException {

    private static final String MESSAGE = "비밀번호가 올바르지 않습니다.";

    public InvalidPassword() {
        super(MESSAGE);
    }

    @Override
    public Integer getStatusCode() {
        return 400;
    }
}
