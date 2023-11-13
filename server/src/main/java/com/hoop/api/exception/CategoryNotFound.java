package com.hoop.api.exception;

/**
 * status -> 400
 */
public class CategoryNotFound extends HoopException {

    private static final String MESSAGE = "카테고리가 잘못 입력되었습니다.";

    public CategoryNotFound() {
        super(MESSAGE);
    }

    @Override
    public Integer getStatusCode() {
        return 400;
    }
}
