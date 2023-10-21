package com.hoop.api.request.sign;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SingIn {

    @NotBlank(message = "소셜 로그인 종류 입력")
    private String category;

    @NotBlank(message = "Access Token")
    private String accessToken;

    @NotBlank(message = "Refresh Token")
    private String refreshToken;

    @Builder
    public SingIn(String category, String accessToken, String refreshToken) {
        this.category = category;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
