package com.hoop.api.exception;


public class FileUploadException extends HoopException {

    private static final String MESSAGE = "파일 업로드에 실패했습니다.";

    public FileUploadException(){
        super(MESSAGE);
    }

    @Override
    public Integer getStatusCode() {
        return 400;
    }
}