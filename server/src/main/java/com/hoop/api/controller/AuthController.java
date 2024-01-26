package com.hoop.api.controller;

import com.hoop.api.exception.CategoryNotFound;
import com.hoop.api.request.user.SignIn;
import com.hoop.api.request.user.SignUp;
import com.hoop.api.request.user.SocialSignUp;
import com.hoop.api.request.user.TokenRequest;
import com.hoop.api.response.user.TokenResponse;
import com.hoop.api.service.user.AuthService;
import com.hoop.api.service.user.JwtService;
import com.hoop.api.service.user.SocialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @PostMapping("/signup/social")
    public TokenResponse signupBySocial(@RequestBody @Valid SocialSignUp socialSignUp) {
        //일단 카카오 로그인만 구현
        Long socialId;
        String category = socialSignUp.getCategory();
        UUID one = UUID.randomUUID();
        TokenResponse tokenResponse = jwtService.createTokenResponse(one.toString());
        switch (category) {
            case "KAKAO" :
                socialId = socialService.getKakaoIdByToken(socialSignUp.getAccessToken());
                authService.signup(SignUp.builder()
                        .email(one.toString())
                        .password(one.toString())
                        .socialId(socialId)
                        .refreshToken(tokenResponse.getRefreshToken())
                        .build());
                break;
            default:
                throw new CategoryNotFound();
        }
        return tokenResponse;
    }

    /**
     * 웹/앱 로그인 이후 코드 전달 -> 코드로 카카오 프로필 가져오기
     * @param signIn
     * @return tokenResponse
     */
    @PostMapping(value = "/signin")
    public TokenResponse signIn (@RequestBody @Valid SignIn signIn) {
        String category = signIn.getCategory();
        Long socialId;
        switch (category) {
            case "KAKAO" :
                socialId = socialService.getKakaoIdByToken(signIn.getAccessToken());
                break;
            default:
                throw new CategoryNotFound();
        }
        return authService.signInBySocialId(socialId);
    }

    @PostMapping(value="/reissue")
    private TokenResponse reissue(@RequestBody @Valid TokenRequest tokenRequest) {
        TokenResponse tokenResponse = jwtService.reissue(tokenRequest);
        return tokenResponse;
    }


}
