package com.hoop.api.response;

import com.hoop.api.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpirationTime;
    private Long refreshTokenExpirationTime;


    @Builder //빌더 패턴 새용
    public TokenResponse(String accessToken, Long accessTokenExpirationTime, String refreshToken, Long refreshTokenExpirationTime) {
        this.accessToken = accessToken;
        this.accessTokenExpirationTime = accessTokenExpirationTime;
        this.refreshToken = refreshToken;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
    }
}
