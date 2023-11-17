package com.hoop.api.exception;

public class ProfileException extends HoopException {

    private static final String MESSAGE = "프로필 생성이 불가능합니다.";

    public ProfileException() {
        super(MESSAGE);
    }

    public ProfileException(String message) {
        super(message);
    }
    @Override
    public Integer getStatusCode() {
        return 400;
    }
}
