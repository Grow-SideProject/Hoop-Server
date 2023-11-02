package com.hoop.api.service.auth;


import com.hoop.api.config.AppConfig;
import com.hoop.api.exception.HeaderInvalid;
import com.hoop.api.exception.Unauthorized;
import com.hoop.api.exception.tokenInvalid;
import com.hoop.api.request.auth.TokenRequest;
import com.hoop.api.response.TokenResponse;
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

    private Date now2 = new Date();

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(appConfig.getJwtKey());
    }

    public TokenResponse createTokenResponse(String subject) {
        String accessToken = this.createAccessToken(subject);
        String refreshToken = this.createRefreshToken(subject);
        // Token Response 생성
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpirationTime(new Date(new Date().getTime() + appConfig.getAccessTokenExpiration()))
                .refreshTokenExpirationTime(new Date(new Date().getTime() + appConfig.getRefreshTokenExpiration()))
                .build();
    }

    public String createAccessToken(String subject) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(subject)
                .signWith(secretKey)
                .setIssuedAt(new Date())
                .setExpiration(new Date(now.getTime() + appConfig.getAccessTokenExpiration()))
                .compact();
    }

    public String createRefreshToken(String subject) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(subject)
                .signWith(secretKey)
                .setIssuedAt(new Date())
                .setExpiration(new Date(now.getTime() + appConfig.getRefreshTokenExpiration()))
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            if (token == null || token.equals("")) throw new HeaderInvalid();
            return token;
        } catch (Exception e) {
            throw new Unauthorized();
        }
    }


    public String getSubject(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(appConfig.getJwtKey())
                    .build()
                    .parseClaimsJws(token);
            return claims.getBody().getSubject();
        } catch (Exception e) {
            throw new tokenInvalid();
        }
    }

    public Date getExpiration(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(appConfig.getJwtKey())
                    .build()
                    .parseClaimsJws(token);
            return claims.getBody().getExpiration();
        } catch (Exception e) {
            throw new tokenInvalid();
        }
    }
    public TokenResponse reissue(TokenRequest tokenRequest) {
        String accessToken = tokenRequest.getAccessToken();
        String refreshToken = tokenRequest.getRefreshToken();
        String accessSubject = this.getSubject(accessToken);
        String refreshSubject = this.getSubject(refreshToken);
        Date refreshTokenExp = this.getExpiration(refreshToken);
        if (1!=1) {
            //  TODO : refresh Token  자동 재발급 로직 고민....
            refreshToken = this.createRefreshToken(refreshSubject);
            refreshTokenExp = new Date(new Date().getTime() + appConfig.getRefreshTokenExpiration());
        }
        accessToken = this.createAccessToken(accessSubject);
        Date accessTokenExp = new Date(new Date().getTime() + appConfig.getAccessTokenExpiration());
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpirationTime(accessTokenExp)
                .refreshTokenExpirationTime(refreshTokenExp)
                .build();
    }
}
