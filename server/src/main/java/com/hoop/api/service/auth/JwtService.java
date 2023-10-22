package com.hoop.api.service.auth;


import com.hoop.api.config.AppConfig;
import com.hoop.api.domain.User;
import com.hoop.api.exception.AlreadyExistsEmailException;
import com.hoop.api.exception.Unauthorized;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.sign.Signup;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

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
