package com.hoop.api.exception;

public class AttendantStatusException extends HoopException {

    private static final String MESSAGE = "유효한 참가 상태가 아닙니다.";

    public AttendantStatusException() {
        super(MESSAGE);
    }
    public AttendantStatusException(String message) {
        super(message);
    }
    @Override
    public Integer getStatusCode() {
        return 400;
    }
}
