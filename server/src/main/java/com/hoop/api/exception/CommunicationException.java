package com.hoop.api.exception;

public class CommunicationException extends HoopException {

    private static final String MESSAGE = "원활한 통신이 되지 않았습니다.";

    public CommunicationException(){
        super(MESSAGE);
    }

    public CommunicationException(String message) {
        super(message);
    }

    @Override
    public Integer getStatusCode() {
        return 405;
    }
}