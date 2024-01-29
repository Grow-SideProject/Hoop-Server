package com.hoop.api.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoop.api.config.HoopMockUser;
import com.hoop.api.constant.Gender;
import com.hoop.api.constant.PlayStyle;
import com.hoop.api.domain.User;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.user.ProfileEdit;
import com.hoop.api.service.user.JwtService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProfileControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;
    private String accessToken;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        User user = User.builder()
                .email("99999")
                .password("99999")
                .socialId(99999L)
                .build();
        userRepository.save(user);
        accessToken = jwtService.createAccessToken("99999");
    }
    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @HoopMockUser
    @DisplayName("CREATE PROFILE")
    void test1() throws Exception {
        // given
        ProfileEdit profileCreate = ProfileEdit.builder()
                .nickName("닉네임이요")
                .birth("2000-01-01")
                .phoneNumber("010-1234-5678")
                .gender(Gender.MALE)
                .address("마포구")
                .desc("강한 타입")
                .playStyle(PlayStyle.DEFENSIVE)
                .build();

        // expected
        mockMvc.perform(post("/profile")
                        .header("Authorization",accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileCreate))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @HoopMockUser
    @DisplayName("GET PROFILE")
    void test2() throws Exception {
        // given
        ProfileEdit profileCreate = ProfileEdit.builder()
                .nickName("닉네임이요")
                .phoneNumber("010-1234-5678")
                .gender(Gender.MALE)
                .address("마포구")
                .desc("강한 타입")
                .playStyle(PlayStyle.DEFENSIVE)
                .build();
        mockMvc.perform(post("/profile")
                        .header("Authorization",accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileCreate))
                )
                .andExpect(status().isOk())
                .andDo(print());

        // expected
        mockMvc.perform(get("/profile")
                        .header("Authorization",accessToken)
                        .contentType(APPLICATION_JSON))

                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @HoopMockUser
    @DisplayName("EDIT PROFILE")
    void test3() throws Exception {
        // given
        ProfileEdit profileCreate = ProfileEdit.builder()
                .nickName("닉네임이요")
                .phoneNumber("010-1234-5678")
                .gender(Gender.MALE)
                .address("마포구")
                .desc("강한 타입")
                .playStyle(PlayStyle.DEFENSIVE)
                .build();
        mockMvc.perform(post("/profile")
                        .header("Authorization",accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileCreate))
                )
                .andExpect(status().isOk())
                .andDo(print());

        ProfileEdit profileEdit = ProfileEdit.builder()
                .nickName("닉네임이요")
                .phoneNumber("010-1234-5678")
                .gender(Gender.FEMALE)
                .address("마포구")
                .desc("강한 타입")
                .playStyle(PlayStyle.DEFENSIVE)
                .build();

        // expected
        mockMvc.perform(post("/profile")
                        .header("Authorization",accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileEdit)))
                .andExpect(status().isOk())
                .andDo(print());
        User user = userRepository.findByNickName("닉네임이요").get();
        assertEquals(Gender.FEMALE, user.getGender());
    }



}










