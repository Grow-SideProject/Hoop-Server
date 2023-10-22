package com.hoop.api.controller;

import com.hoop.api.config.AppConfig;
import com.hoop.api.domain.User;
import com.hoop.api.exception.Unauthorized;
import com.hoop.api.request.sign.Signup;
import com.hoop.api.request.sign.SingIn;
import com.hoop.api.response.KakaoAuth;
import com.hoop.api.response.KakaoProfile;
import com.hoop.api.response.TokenResponse;
import com.hoop.api.service.auth.AuthService;
import com.hoop.api.service.auth.JwtService;
import com.hoop.api.service.auth.KakaoService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final KakaoService kakaoService;

    @Value("${spring.social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;

    /**
     * 회원 가입
     * @param signup
     */
    @PostMapping("/signup")
    public void signup(@RequestBody Signup signup) {
        authService.signup(signup);
    }

    /**
     * 웹/앱 로그인 이후 코드 전달 -> 코드로 카카오 프로필 가져오기
     * @param singIn
     * @return tokenResponse
     */
    @PostMapping(value = "/signin")
    public @ResponseBody TokenResponse signIn (@RequestBody SingIn singIn) {
        Long kakaoId = 12345L;
        if (singIn.getCategory().equals("KAKAO")){
            if (!singIn.getAccessToken().equals("MOCKTOKEN")) {
                KakaoProfile profile =kakaoService.getKakaoProfile(singIn.getAccessToken());
                kakaoId = profile.getId();
            }
            Optional<User> user= kakaoService.getByKakao(kakaoId);
            if (user.isEmpty()){
                authService.signupByKakao(Signup.builder().email(Long.toString(kakaoId)).password(Long.toString(kakaoId)).kakao(kakaoId).build());
            }
            String accessToken = jwtService.createAccessToken(Long.toString(kakaoId));
            String refreshToken = jwtService.createRefreshToken(Long.toString(kakaoId));
            TokenResponse tokenResponse = TokenResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .accessTokenExpirationTime(jwtService.getAccessTokenExpiration())
                    .refreshTokenExpirationTime(jwtService.getRefreshTokenExpiration())
                    .build();
            return tokenResponse;
        }
        throw new Unauthorized();
    }

    @GetMapping(value="kakao")
    @ResponseBody
    public String getKakaoAccess(String code) {
        return kakaoService.getKakaoTokenInfo(code).getAccess_token();
    }

}
