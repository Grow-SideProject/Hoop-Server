package com.hoop.api.response;

import lombok.Builder;

public class TokenResponse {
    private String grantType;
    private String accessToken;
    private String refreshToken;

    private Long accessTokenExpirationTime;
    private Long refreshTokenExpirationTime;


    @Builder //빌더 패턴 새용
    public TokenResponse(String grantType, String accessToken, Long accessTokenExpirationTime, String refreshToken, Long refreshTokenExpirationTime) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.accessTokenExpirationTime = accessTokenExpirationTime;
        this.refreshToken = refreshToken;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
    }
}