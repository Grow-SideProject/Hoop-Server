package com.hoop.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoop.api.domain.User;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.user.SignIn;
import com.hoop.api.request.user.SignUp;
import com.hoop.api.request.user.SocialSignUp;
import com.hoop.api.service.user.SocialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private SocialService socialService;


    private SocialService authService;


    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("SIGNUP")
    void signup() throws Exception{
        SignUp signup = SignUp.builder()
                .email("temp@gmail.com")
                .password("1234")
                .socialId(12345L)
                .build();
        mockMvc.perform(post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signup))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("SIGNUPBYKAKAO")
    void signupByKakao() throws Exception {
        // given
        SocialSignUp signup = SocialSignUp
                .builder()
                .category("KAKAO")
                .accessToken("MOCKTOKEN")
                .build();

        when(socialService.getKakaoIdByToken("MOCKTOKEN")).thenReturn(12345L);

        // expected
        mockMvc.perform(post("/auth/signup/social")
                        .content(objectMapper.writeValueAsString(signup))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        assertEquals(1, userRepository.count());

    }

    @Test
    @DisplayName("SIGNIN")
    void signIn() throws Exception {
        User user = User.builder()
                .email("temp@gmail.com")
                .password("1234")
                .socialId(12345L)
                .build();
        userRepository.saveAndFlush(user);
        when(socialService.getKakaoIdByToken("MOCKTOKEN")).thenReturn(12345L);
        SignIn signIn = SignIn.builder()
                .accessToken("MOCKTOKEN")
                .category("KAKAO")
                .build();

        mockMvc.perform(post("/auth/signin")
                        .content(objectMapper.writeValueAsString(signIn))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    @DisplayName("SIGNIN FAIL")
    void signInFail() throws Exception {
        when(socialService.getKakaoIdByToken("MOCKTOKEN")).thenReturn(12345L);
        SignIn signIn = SignIn.builder()
                .accessToken("MOCKTOKEN")
                .category("KAKAO")
                .build();
        mockMvc.perform(post("/auth/signin")
                        .content(objectMapper.writeValueAsString(signIn))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}
