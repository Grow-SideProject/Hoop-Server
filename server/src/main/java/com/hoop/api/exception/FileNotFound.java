package com.hoop.api.exception;

public class FileNotFound extends HoopException {

    private static final String MESSAGE = "파일을 찾을 수 없습니다.";

    public FileNotFound() {
        super(MESSAGE);
    }

    @Override
    public Integer getStatusCode() {
        return 404;
    }
}

