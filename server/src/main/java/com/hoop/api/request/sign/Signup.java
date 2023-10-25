package com.hoop.api.request.sign;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Signup {
    private String email;
    private String password;
    private long kakao;
}
