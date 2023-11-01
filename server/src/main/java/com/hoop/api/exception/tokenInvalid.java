package com.hoop.api.exception;

/**
 * status -> 401
 */
public class tokenInvalid extends HoopException {

    private static final String MESSAGE = "토큰 정보가 유효하지 않습니다.";

    public tokenInvalid() {
        super(MESSAGE);
    }

    @Override
    public Integer getStatusCode() {
        return 401;
    }
}
