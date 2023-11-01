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
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/helloworld")
public class HelloController {

    private static final String HELLO = "helloworld-nice to meet you";
    private final AuthService authService;
    private final JwtService jwtService;

    @Setter
    @Getter
    public static class Hello {
        private String message;
    }

    @GetMapping(value = "/string")
    @ResponseBody
    public String helloworldString() {
        log.debug("Helloworld");
        log.info("Helloworld");
        return HELLO;
    }
    @GetMapping(value = "/json")
    @ResponseBody
    public Hello helloworldJson() {
        Hello hello = new Hello();
        hello.message = HELLO;
        return hello;
    }

    @GetMapping(value = "/page")
    public String helloworld() {
        return "helloworld";
    }

    @GetMapping("/long-process")
    @ResponseBody
    public String pause() throws InterruptedException {
        Thread.sleep(10000);
        return "Process finished";
    }

    @GetMapping("/temp-auth")
    public @ResponseBody TokenResponse signupBySocial() {
        //일단 카카오 로그인만 구현
        Long id = 99999L;
        String accessToken = jwtService.createAccessToken(Long.toString(id));
        String refreshToken = jwtService.createRefreshToken(Long.toString(id));
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpirationTime(jwtService.getAccessTokenExpiration())
                .refreshTokenExpirationTime(jwtService.getRefreshTokenExpiration())
                .build();
    }
}