package com.hoop.api.controller;

import com.hoop.api.config.AppConfig;
import com.hoop.api.domain.User;
import com.hoop.api.request.Signup;
import com.hoop.api.response.KakaoAuth;
import com.hoop.api.response.KakaoProfile;
import com.hoop.api.service.auth.AuthService;
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
    private final AppConfig appConfig;
    private final KakaoService kakaoService;

    @Value("${spring.social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;

    @PostMapping("/signup")
    public void signup(@RequestBody Signup signup) {
        authService.signup(signup);
    }


    /**
     * 웹/앱 로그인 이후 코드 전달 -> 코드로 카카오 프로필 가져오기
     */
    @GetMapping(value = "/kakao")
    public @ResponseBody String redirectKakao (@RequestParam String code, HttpServletResponse response) throws IOException {
        Long kakaoId = 0L;
        if("mockCode".equals(code)) {
            kakaoId = 1234L;
        }
        else {
            KakaoAuth kakaoAuth = kakaoService.getKakaoTokenInfo(code);
            KakaoProfile profile =kakaoService.getKakaoProfile(kakaoAuth.getAccess_token());
            kakaoId = profile.getId();
        }
        Optional<User> user= kakaoService.getByKakao(kakaoId);
////         이후 만약 empty일 경우, 회원가입으로 이동 아닐 경우 로그인 진행
        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());
        if (user.isEmpty()){
            //임시 더미 아이디 생성
            authService.signupByKakao(Signup.builder().email(Long.toString(kakaoId)).password(Long.toString(kakaoId)).kakao(kakaoId).build());
            String jws = Jwts.builder()
                    .setSubject(Long.toString(kakaoId))
                    .signWith(key)
                    .setIssuedAt(new Date())
                    .compact();
            return jws;
        }
        else{
            String jws = Jwts.builder()
                    .setSubject(Long.toString(kakaoId))
                    .signWith(key)
                    .setIssuedAt(new Date())
                    .compact();
            return jws;
        }
    }
}
