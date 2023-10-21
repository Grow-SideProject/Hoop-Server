package com.hoop.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoop.api.config.AppConfig;
import com.hoop.api.config.HoopMockUser;
import com.hoop.api.domain.User;
import com.hoop.api.repository.post.PostRepository;
import com.hoop.api.repository.UserRepository;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
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
    @DisplayName("USER GET")
    void test1() throws Exception {
        User user = User.builder()
                .name("이름")
                .email("temp123@gmail.com")
                .password("1234")
                .build();
        userRepository.saveAndFlush(user);
        // expected
        mockMvc.perform(get("/profile/{userId}", user.getId())
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("profile-inquiry",
                        pathParameters(
                                parameterWithName("userId").description("유저 ID")
                        )
                ));
    }

    @Test
    @HoopMockUser
    @DisplayName("UPDATE PROFILE")
    void test2() throws Exception {
        User user = User.builder()
                .name("이름")
                .email("hodolman88@gmail.com")
                .password("1234")
                .build();
        userRepository.saveAndFlush(user);

        List<String> myList = Arrays.asList("포인트가드", "센터");
        ProfileEdit profileEdit = ProfileEdit.builder()
                .name("닉네임")
                .height(180L)
                .weight(70L)
                .desc("강한 타입")
                .positions(myList)
                .build();


        // expected
        mockMvc.perform(patch("/profile/{userId}", user.getId())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileEdit)))
                .andDo(document(
                        "profile-update",
                        requestFields(
                                fieldWithPath("name").description("이름")
                                        .attributes(key("constraint").value("닉네임")),
                                fieldWithPath("height").description("키"),
                                fieldWithPath("weight").description("몸무게"),
                                fieldWithPath("desc").description("상세 내용").optional(),
                                fieldWithPath("positions[]").description("포지션을 리스트로 입력")
                        )
                ));
    }
}






