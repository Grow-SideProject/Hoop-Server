package com.hoop.api.controller;

import com.hoop.api.domain.User;
import com.hoop.api.exception.Unauthorized;
import com.hoop.api.exception.UserNotFound;
import com.hoop.api.request.auth.SignIn;
import com.hoop.api.request.auth.SignUp;
import com.hoop.api.request.auth.SocialSignUp;
import com.hoop.api.response.TokenResponse;
import com.hoop.api.service.auth.AuthService;
import com.hoop.api.service.auth.JwtService;
import com.hoop.api.service.auth.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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
    public void signup(@RequestBody SignUp signup) {
        authService.signup(signup);
    }

    /**
     * 회원 가입
     * @param
     */
    @PostMapping("/signup/social")
    public @ResponseBody String signupBySocial(@RequestBody SocialSignUp socialSignUp) {
        //일단 카카오 로그인만 구현
        String category = socialSignUp.getCategory();
        switch (category) {
            case "KAKAO" :
                Long id = kakaoService.getKakaoIdByToken(socialSignUp.getAccessToken());
                authService.signup(SignUp.builder()
                        .email(Long.toString(id))
                        .password(Long.toString(id))
                        .socialId(id)
                        .build());
                break;
            default:
                throw new Unauthorized();
        }

        return "KAKAO SIGNUP SUCCESS";
    }

    /**
     * 웹/앱 로그인 이후 코드 전달 -> 코드로 카카오 프로필 가져오기
     * @param signIn
     * @return tokenResponse
     */
    @PostMapping(value = "/signin")
    public @ResponseBody TokenResponse signIn (@RequestBody SignIn signIn) {
        String category = signIn.getCategory();
        Long id;
        switch (category) {
            case "KAKAO" :
                id = kakaoService.getKakaoIdByToken(signIn.getAccessToken());
                Optional<User> user= kakaoService.getByKakao(id);
                if (user.isEmpty()){
                    throw new UserNotFound();
                }
                break;
            default:
                throw new Unauthorized();
        }
        String accessToken = jwtService.createAccessToken(Long.toString(id));
        String refreshToken = jwtService.createRefreshToken(Long.toString(id));
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpirationTime(jwtService.getAccessTokenExpiration())
                .refreshTokenExpirationTime(jwtService.getRefreshTokenExpiration())
                .build();
    }

    @GetMapping(value="kakao")
    @ResponseBody
    public String getKakaoAccess(String code) {
        log.info("code = {}",code);
        String token = kakaoService.getKakaoTokenInfo(code).getAccess_token();
        log.info("token = {}",token);
        return token;
    }


}
