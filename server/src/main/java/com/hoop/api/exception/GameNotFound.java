package com.hoop.api.exception;

/**
 * status -> 404
 */
public class GameNotFound extends HoopException {

    private static final String MESSAGE = "존재하지 않는 게임 또는 참석자입니다.";

    public GameNotFound() {
        super(MESSAGE);
    }

    @Override
    public Integer getStatusCode() {
        return 404;
    }
}
