package com.hoop.api.exception;

/**
 * status -> 401
 */
public class HeaderInvalid extends HoopException {

    private static final String MESSAGE = "헤더 정보를 확인해주길 바랍니다.";

    public HeaderInvalid() {
        super(MESSAGE);
    }

    @Override
    public Integer getStatusCode() {
        return 401;
    }
}
