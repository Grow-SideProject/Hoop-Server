package com.hoop.api.controller;

import com.hoop.api.domain.User;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.user.SignUp;
import com.hoop.api.response.TokenResponse;
import com.hoop.api.service.user.AuthService;
import com.hoop.api.service.user.JwtService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
    private final UserRepository userRepository;

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
        Optional<User> user =userRepository.findBySocialId(id);
        if (user.isEmpty())  authService.signup(SignUp.builder()
                .email(Long.toString(id))
                .password(Long.toString(id))
                .socialId(id)
                .build());
        User user2 =user.get();
        user2.setRefreshToken(refreshToken);
        userRepository.save(user2);
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
//                .accessTokenExpirationTime(jwtService.getAccessTokenExpiration())
//                .refreshTokenExpirationTime(jwtService.getRefreshTokenExpiration())
                .build();
    }
}