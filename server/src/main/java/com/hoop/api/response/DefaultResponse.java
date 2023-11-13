package com.hoop.api.response;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;


@Getter
public class DefaultResponse {

    private String code;
    private String message;

    private HashMap<String, Object> body;

    @Builder
    public DefaultResponse(String code, String message, HashMap<String, Object> body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public DefaultResponse() {
        this.code = "200";
        this.message = "응답 성공";
    }
}













