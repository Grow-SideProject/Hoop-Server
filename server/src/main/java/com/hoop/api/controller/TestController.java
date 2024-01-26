package com.hoop.api.controller;

import com.hoop.api.constant.*;
import com.hoop.api.domain.User;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.user.SignUp;
import com.hoop.api.response.user.TokenResponse;
import com.hoop.api.service.user.AuthService;
import com.hoop.api.service.user.JwtService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

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


    /*
    ** ENUM TEST
     */
    @GetMapping("/abilities")
    public HashMap<String, String> getAbilities(){
        HashMap<String, String> response = new HashMap<String, String>();
        for (Ability ability : Ability.values()) {
            response.put(ability.name(), ability.getValue());
        }
        return response;
    }
    @GetMapping("/attendant-status")
    public HashMap<String, String> getAttendantStatus(){
        HashMap<String, String> response = new HashMap<String, String>();
        for (AttendantStatus attendantStatus : AttendantStatus.values()) {
            response.put(attendantStatus.name(), attendantStatus.getValue());
        }
        return response;
    }
    @GetMapping("/catergories")
    public HashMap<String, String> getCatergories(){
        HashMap<String, String> response = new HashMap<String, String>();
        for (GameCategory gameCategory : GameCategory.values()) {
            response.put(gameCategory.name(), gameCategory.getValue());
        }
        return response;
    }
    @GetMapping("/genders")
    public HashMap<String, String> getGenders(){
        HashMap<String, String> response = new HashMap<String, String>();
        for (Gender gender : Gender.values()) {
            response.put(gender.name(), gender.getValue());
        }
        return response;
    }
    @GetMapping("/levels")
    public HashMap<String, String> getLevels(){
        HashMap<String, String> response = new HashMap<String, String>();
        for (Level level : Level.values()) {
            response.put(level.name(), level.getValue());
        }
        return response;
    }
    @GetMapping("/play-styles")
    public HashMap<String, String> getPlayStyle(){
        HashMap<String, String> response = new HashMap<String, String>();
        for (PlayStyle playStyle : PlayStyle.values()) {
            response.put(playStyle.name(), playStyle.getValue());
        }
        return response;
    }
}