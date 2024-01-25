package com.hoop.api.exception;

public class RecruitmentClosedException extends HoopException {

    private static final String MESSAGE = "모집이 마감되었습니다.";

    public RecruitmentClosedException() {
        super(MESSAGE);
    }
    public RecruitmentClosedException(String message) {
        super(message);
    }

    @Override
    public Integer getStatusCode() {
        return 400;
    }
}
