package com.hoop.api.response.user;

import com.hoop.api.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    private Date accessTokenExpirationTime;
    private Date refreshTokenExpirationTime;


    @Builder //빌더 패턴 새용
    public TokenResponse(String accessToken, Date accessTokenExpirationTime, String refreshToken, Date refreshTokenExpirationTime) {
        this.accessToken = accessToken;
        this.accessTokenExpirationTime = accessTokenExpirationTime;
        this.refreshToken = refreshToken;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
    }
}
