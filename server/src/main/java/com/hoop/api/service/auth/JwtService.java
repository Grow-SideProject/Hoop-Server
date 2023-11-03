package com.hoop.api.service.auth;


import com.hoop.api.config.AppConfig;
import com.hoop.api.domain.User;
import com.hoop.api.exception.HeaderInvalid;
import com.hoop.api.exception.Unauthorized;
import com.hoop.api.exception.tokenInvalid;
import com.hoop.api.repository.UserRepository;
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
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final AppConfig appConfig;

    private SecretKey secretKey;

    private  final UserRepository userRepository;

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

    /**
     * 만료일이 한달 이내라면
     * refresh token을 재발급하고
     * access token을 발급한다.
     * 만료일이 한달 이상이라면
     * access token만을 발급한다.
     */
    @Transactional
    public TokenResponse reissue(TokenRequest tokenRequest) {
        Date now = new Date();
        String refreshToken = tokenRequest.getRefreshToken();
        String subject = this.getSubject(refreshToken);
        User user =userRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new tokenInvalid());
        /*
         refresh Token이 valid한다면 access token을 발급한다.
         */
        String accessToken = this.createAccessToken(subject);
        Date accessTokenExp = new Date(now.getTime() + appConfig.getAccessTokenExpiration());
        /*
         만료일이 한달 이내라면 Refresh Token 또한 재발급
         */
        Date refreshTokenExp = this.getExpiration(refreshToken); // refresh token 만료일
        LocalDate nextMonth = LocalDate.now().plusMonths(1); // 지금부터 한달 뒤
        LocalDate refreshTokenExpLocalDate = refreshTokenExp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (nextMonth.isAfter(refreshTokenExpLocalDate)) {
            refreshToken = this.createRefreshToken(subject); // 재발급
            refreshTokenExp = new Date(now.getTime() + appConfig.getRefreshTokenExpiration()); // 만료일 또한 변경
            user.setRefreshToken(refreshToken);
            userRepository.save(user);
        }
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpirationTime(accessTokenExp)
                .refreshTokenExpirationTime(refreshTokenExp)
                .build();
    }
}
