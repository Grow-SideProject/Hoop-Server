package com.hoop.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoop.api.config.HoopMockUser;
import com.hoop.api.domain.Post;
import com.hoop.api.domain.Profile;
import com.hoop.api.domain.User;
import com.hoop.api.repository.ProfileRepository;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.repository.post.PostRepository;
import com.hoop.api.request.post.PostCreate;
import com.hoop.api.request.post.PostEdit;
import com.hoop.api.request.profile.ProfileCreate;
import com.hoop.api.request.profile.ProfileEdit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
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
    private ProfileRepository profileRepository;

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @HoopMockUser
    @DisplayName("CREATE PROFILE")
    void test1() throws Exception {
        // given
        List<String> myList = Arrays.asList("포인트가드", "센터");
        ProfileCreate profileCreate = ProfileCreate.builder()
                .height(180)
                .weight(70)
                .desc("강한 타입")
                .positions(myList)
                .build();

        // expected
        mockMvc.perform(post("/profile/create")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileCreate))
                )
                .andExpect(status().isOk())
                .andDo(print());
        assertEquals(1, profileRepository.count());
    }

    @Test
    @DisplayName("GET PROFILE")
    void test2() throws Exception {
        // given
        User user = User.builder()
                .email("temp@gmail.com")
                .password("1234")
                .build();
        userRepository.save(user);
        List<String> myList = Arrays.asList("포인트가드", "센터");

        Profile profile = Profile.builder()
                .height(180)
                .weight(70)
                .desc("강한 타입")
                .positions(myList)
                .user(user)
                .build();
        profileRepository.save(profile);

        // expected
        mockMvc.perform(get("/profile/{userId}", user.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    @HoopMockUser
    @DisplayName("EDIT PROFILE")
    void test3() throws Exception {
        // given
        User user = User.builder()
                .email("temp@gmail.com")
                .password("1234")
                .build();
        userRepository.save(user);
        List<String> myList = Arrays.asList("포인트가드", "센터");

        Profile profile = Profile.builder()
                .height(180)
                .weight(70)
                .name("닉네임")
                .desc("강한 타입")
                .positions(myList)
                .user(user)
                .build();
        profileRepository.save(profile);

        myList = Arrays.asList("파워포워드", "센터");

        ProfileEdit profileEdit = ProfileEdit.builder()
                .height(200)
                .weight(100)
                .desc("매우 강한 타입")
                .positions(myList)
                .build();

        // expected
        mockMvc.perform(patch("/profile/{userId}", user.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileEdit)))
                .andExpect(status().isOk())
                .andDo(print());
    }

}










