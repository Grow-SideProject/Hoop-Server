package com.hoop.api.exception;

import lombok.Getter;

/**
 * status -> 400
 */
@Getter
public class InvalidRequest extends HoopException {

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public Integer getStatusCode() {
        return 400;
    }
}
