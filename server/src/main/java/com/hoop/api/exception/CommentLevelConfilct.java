package com.hoop.api.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

/**
 * status -> 407
 */
public class CommentLevelConfilct extends HoopException {

    private static final String MESSAGE = "답글의 답글을 달 수 없습니다.";

    public CommentLevelConfilct() {
        super(MESSAGE);
    }

    @Override
    public Integer getStatusCode() {
        return 409;
    }
}
