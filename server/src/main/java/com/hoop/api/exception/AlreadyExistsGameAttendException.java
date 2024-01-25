package com.hoop.api.exception;

public class AlreadyExistsGameAttendException extends HoopException {

    private static final String MESSAGE = "이미 참가한 게임입니다.";

    public AlreadyExistsGameAttendException() {
        super(MESSAGE);
    }
    public AlreadyExistsGameAttendException(String message) {
        super(message);
    }

    @Override
    public Integer getStatusCode() {
        return 409;
    }
}
