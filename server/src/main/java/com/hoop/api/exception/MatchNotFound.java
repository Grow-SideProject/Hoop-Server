package com.hoop.api.exception;

/**
 * status -> 404
 */
public class MatchNotFound extends HoopException {

    private static final String MESSAGE = "존재하지 않는 매칭입니다.";

    public MatchNotFound() {
        super(MESSAGE);
    }

    @Override
    public Integer getStatusCode() {
        return 404;
    }
}
