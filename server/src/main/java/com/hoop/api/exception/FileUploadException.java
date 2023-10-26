package com.hoop.api.exception;

import java.io.IOException;

public class FileUploadException extends HoopException {


    private static final String MESSAGE = "파일 업로드에 실패했습니다.";

    public FileUploadException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
