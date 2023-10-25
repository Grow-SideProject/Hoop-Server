package com.hoop.api.service.auth;


import com.hoop.api.config.AppConfig;
import com.hoop.api.exception.Unauthorized;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final AppConfig appConfig;

    private SecretKey secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(appConfig.getJwtKey());
    }

    public String createAccessToken(String subject) {
        Date now = new Date();
        String token = Jwts.builder()
                .setSubject(subject)
                .signWith(secretKey)
                .setIssuedAt(new Date())
                .setExpiration(new Date(now.getTime() + appConfig.getAccessTokenExpiration()))
                .compact();
        return token;
    }

    public String createRefreshToken(String subject) {
        Date now = new Date();
        String token = Jwts.builder()
                .setSubject(subject)
                .signWith(secretKey)
                .setIssuedAt(new Date())
                .setExpiration(new Date(now.getTime() + appConfig.getRefreshTokenExpiration()))
                .compact();
        return token;
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token == "" ) throw new Unauthorized();
        return token;
    }


    public String getSubject(String token) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(appConfig.getJwtKey())
                .build()
                .parseClaimsJws(token);
        return claims.getBody().getSubject();
    }

    public Long getAccessTokenExpiration(){
        return appConfig.getAccessTokenExpiration();
    }

    public Long getRefreshTokenExpiration(){
        return appConfig.getRefreshTokenExpiration();
    }
}
