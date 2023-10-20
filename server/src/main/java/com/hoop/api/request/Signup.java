package com.hoop.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Signup {

    private String email;
    private String password;
    private String name;
    private long kakao;
}
