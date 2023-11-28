package com.hoop.api.request.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUp {
    private String email;
    private String password;
    private Long socialId;
    private String refreshToken;
}
