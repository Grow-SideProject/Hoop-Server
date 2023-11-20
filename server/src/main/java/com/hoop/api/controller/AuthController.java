package com.hoop.api.controller;

import com.hoop.api.exception.CategoryNotFound;
import com.hoop.api.request.user.SignIn;
import com.hoop.api.request.user.SignUp;
import com.hoop.api.request.user.SocialSignUp;
import com.hoop.api.request.user.TokenRequest;
import com.hoop.api.response.TokenResponse;
import com.hoop.api.service.user.AuthService;
import com.hoop.api.service.user.JwtService;
import com.hoop.api.service.user.SocialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final SocialService socialService;


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
    public @ResponseBody TokenResponse signupBySocial(@RequestBody SocialSignUp socialSignUp) {
        //일단 카카오 로그인만 구현
        Long socialId;
        String category = socialSignUp.getCategory();
        switch (category) {
            case "KAKAO" :
                socialId = socialService.getKakaoIdByToken(socialSignUp.getAccessToken());
                authService.signup(SignUp.builder()
                        .email(Long.toString(socialId))
                        .password(Long.toString(socialId))
                        .socialId(socialId)
                        .build());
                break;
            default:
                throw new CategoryNotFound();
        }
        TokenResponse tokenResponse = jwtService.createTokenResponse(Long.toString(socialId));
        authService.setRefreshTokenBySocialId(socialId,tokenResponse.getRefreshToken());
        return tokenResponse;
    }

    /**
     * 웹/앱 로그인 이후 코드 전달 -> 코드로 카카오 프로필 가져오기
     * @param signIn
     * @return tokenResponse
     */
    @PostMapping(value = "/signin")
    public @ResponseBody TokenResponse signIn (@RequestBody SignIn signIn) {
        String category = signIn.getCategory();
        Long socialId;
        switch (category) {
            case "KAKAO" :
                socialId = socialService.getKakaoIdByToken(signIn.getAccessToken());
                break;
            default:
                throw new CategoryNotFound();
        }
        TokenResponse tokenResponse = jwtService.createTokenResponse(Long.toString(socialId));
        authService.setRefreshTokenBySocialId(socialId,tokenResponse.getRefreshToken());
        return tokenResponse;
    }

    @GetMapping(value="kakao")
    @ResponseBody
    public String getKakaoAccess(String code) {
        log.info("code = {}",code);
        String token = socialService.getKakaoTokenInfo(code).getAccess_token();
        log.info("token = {}",token);
        return token;
    }

    @GetMapping(value="/login")
    @ResponseBody
    public String login() {
        return "success";
    }


    @PostMapping(value="/reissue")
    @ResponseBody
    private TokenResponse reissue(@RequestBody TokenRequest tokenRequest) {
        TokenResponse tokenResponse = jwtService.reissue(tokenRequest);
        return tokenResponse;
    }

}
