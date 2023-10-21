package com.hoop.api.controller;

import com.hoop.api.config.AppConfig;
import com.hoop.api.domain.User;
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
        Long kakaoId = null;
        if (singIn.getCategory().equals("KAKAO")){
            KakaoProfile profile =kakaoService.getKakaoProfile(singIn.getAccessToken());
            kakaoId = profile.getId();
        }
        Optional<User> user= kakaoService.getByKakao(kakaoId);
        //만약 empty일 경우, 회원가입으로 이동 아닐 경우 로그인 진행
        if (user.isEmpty()){
            //임시 더미 아이디 생성
            authService.signupByKakao(Signup.builder().email(Long.toString(kakaoId)).password(Long.toString(kakaoId)).kakao(kakaoId).build());
            String accessToken = jwtService.createToken(Long.toString(kakaoId));
            String refreshToken = jwtService.createToken(Long.toString(kakaoId));
            return TokenResponse.builder()
                    .grantType("")
                    .accessTokenExpirationTime(1234L)
                    .refreshTokenExpirationTime(1234L)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
        else{
            authService.signupByKakao(Signup.builder().email(Long.toString(kakaoId)).password(Long.toString(kakaoId)).kakao(kakaoId).build());
            String accessToken = jwtService.createToken(Long.toString(kakaoId));
            String refreshToken = jwtService.createToken(Long.toString(kakaoId));
            return TokenResponse.builder()
                    .grantType("")
                    .accessTokenExpirationTime(1234L)
                    .refreshTokenExpirationTime(1234L)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
    }
}
