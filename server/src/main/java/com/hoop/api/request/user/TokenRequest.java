package com.hoop.api.request.user;

import lombok.*;

@Data
@NoArgsConstructor
public class TokenRequest {
    private String accessToken;
    private String refreshToken;

    @Builder //빌더 패턴 새용
    public TokenRequest(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
