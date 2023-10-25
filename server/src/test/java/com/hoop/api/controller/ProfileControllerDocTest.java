package com.hoop.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoop.api.config.AppConfig;
import com.hoop.api.config.HoopMockUser;
import com.hoop.api.domain.Profile;
import com.hoop.api.domain.User;
import com.hoop.api.repository.ProfileRepository;
import com.hoop.api.repository.post.PostRepository;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.profile.ProfileCreate;
import com.hoop.api.request.profile.ProfileEdit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.hoop.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class ProfileControllerDocTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AppConfig appConfig;

    @AfterEach
    void clean() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @HoopMockUser
    @DisplayName("CREATE PROFILE")
    void test1() throws Exception {
        // given
        List<String> myList = Arrays.asList("포인트가드", "센터");
        ProfileCreate profileCreate = ProfileCreate.builder()
                .name("닉네임")
                .height(180)
                .weight(70)
                .desc("강한 타입")
                .positions(myList)
                .build();

        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.post("/profile/create")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileCreate))
                )
                .andExpect(status().isOk())
                .andDo(document(
                        "profile-create",
                        requestFields(
                                fieldWithPath("name").description("이름(닉네임)"),
                                fieldWithPath("height").description("키"),
                                fieldWithPath("weight").description("몸무게"),
                                fieldWithPath("desc").description("상세 내용"),
                                fieldWithPath("positions[]").description("포지션을 리스트로 입력(추후 수정 예정)")
                        )
                ));
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
        userRepository.saveAndFlush(user);
        List<String> myList = Arrays.asList("포인트가드", "센터");

        Profile profile = Profile.builder()
                .name("닉네임")
                .height(180)
                .weight(70)
                .desc("강한 타입")
                .positions(myList)
                .user(user)
                .build();
        profileRepository.saveAndFlush(profile);

        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.get("/profile/{userId}", user.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("profile-inquiry",
                        pathParameters(
                                parameterWithName("userId").description("유저 ID")
                        )
                ));
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
        userRepository.saveAndFlush(user);
        List<String> myList = Arrays.asList("포인트가드", "센터");
        Profile profile = Profile.builder()
                .height(180)
                .weight(70)
                .name("닉네임")
                .desc("강한 타입")
                .positions(myList)
                .user(user)
                .build();
        profileRepository.saveAndFlush(profile);

        myList = Arrays.asList("파워포워드", "센터");

        ProfileEdit profileEdit = ProfileEdit.builder()
                .height(200)
                .weight(100)
                .desc("매우 강한 타입")
                .positions(myList)
                .build();

        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.patch("/profile/{userId}", user.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileEdit)))
                .andExpect(status().isOk())
                .andDo(document(
                        "profile-update",
                        requestFields(
                                fieldWithPath("name").description("이름(닉네임)"),
                                fieldWithPath("height").description("키"),
                                fieldWithPath("weight").description("몸무게"),
                                fieldWithPath("desc").description("상세 내용"),
                                fieldWithPath("positions[]").description("포지션을 리스트로 입력(추후 수정)")
                        )
                ));
    }
}






