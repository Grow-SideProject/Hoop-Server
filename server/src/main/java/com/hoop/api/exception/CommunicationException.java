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

    /**
     * status -> 404
     */
    public static class CommentNotFound extends HoopException {

        private static final String MESSAGE = "존재하지 않는 댓글입니다.";

        public CommentNotFound() {
            super(MESSAGE);
        }

        @Override
        public Integer getStatusCode() {
            return 404;
        }
    }
}