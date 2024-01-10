package com.hoop.api.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@ToString
public class SocialSignUp {

    @NotBlank(message = "소셜 로그인 종류 입력")
    private String category;

    @NotBlank(message = "Access Token")
    private String accessToken;

    @Builder
    public SocialSignUp(String category, String accessToken) {
        this.category = category;
        this.accessToken = accessToken;
    }
}
