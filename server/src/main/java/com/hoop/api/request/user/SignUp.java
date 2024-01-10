package com.hoop.api.request.user;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUp {
    private String email;
    private String password;
    private Long socialId;
    private String refreshToken;
    @Builder
    public SignUp(String email, String password, Long socialId, String refreshToken) {
        this.email = email;
        this.password = password;
        this.socialId = socialId;
        this.refreshToken = refreshToken;
    }
}
