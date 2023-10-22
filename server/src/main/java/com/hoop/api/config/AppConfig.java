package com.hoop.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Base64;

@Data
@ConfigurationProperties(prefix = "hoop")
public class AppConfig {

    private byte[] jwtKey;

    private long accessTokenExpiration = 1000L * 60 * 60 * 24 * 7; // 일주일
    private long refreshTokenExpiration = 1000L * 60 * 60 * 24 * 30 ; // 한달


    public void setJwtKey(String jwtKey) {
        this.jwtKey = Base64.getDecoder().decode(jwtKey);
    }

    public byte[] getJwtKey() {
        return jwtKey;
    }
}
