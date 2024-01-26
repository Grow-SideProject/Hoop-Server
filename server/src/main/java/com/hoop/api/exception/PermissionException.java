package com.hoop.api.exception;

public class PermissionException extends HoopException {

    private static final String MESSAGE = "권한이 없습니다.";

    public PermissionException() {
        super(MESSAGE);
    }
    public PermissionException(String message) {
        super(message);
    }

    @Override
    public Integer getStatusCode() {
        return 403;
    }
}
