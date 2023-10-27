package com.hoop.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoop.api.config.AppConfig;
import com.hoop.api.config.HoopMockUser;
import com.hoop.api.domain.Post;
import com.hoop.api.domain.User;
import com.hoop.api.repository.post.PostRepository;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.sign.SignUp;
import com.hoop.api.request.post.PostCreate;
import com.hoop.api.request.sign.SignIn;
import com.hoop.api.request.sign.SocialSignUp;
import com.hoop.api.service.auth.KakaoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
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
public class AuthControllerDocTest {

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

    @MockBean
    private KakaoService kakaoService;

    @AfterEach
    void clean() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("SIGNUP")
    void signup() throws Exception {
        // given
        SignUp signup = SignUp.builder()
                .email("temp@gmail.com")
                .password("1234")
                .build();

        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/signup")
                        .content(objectMapper.writeValueAsString(signup))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auth-signup",
                        requestFields(
                                fieldWithPath("email").description("이메일")
                                        .attributes(key("constraint").value("소셜 로그인일 경우 id")),
                                fieldWithPath("password").description("비밀번호")
                                        .attributes(key("constraint").value("소셜 로그인일 경우 id")),
                                fieldWithPath("kakao").description("kakao ID").optional()
                        )
                ));
    }

    @Test
    @DisplayName("SIGNUP")
    void signupByKakao() throws Exception {
        // given
        SocialSignUp signup = SocialSignUp
                .builder()
                .category("KAKAO")
                .accessToken("MOCKTOKEN")
                .build();

        when(kakaoService.getKakaoIdByToken("MOCKTOKEN")).thenReturn(12345L);

        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/signup/social")
                        .content(objectMapper.writeValueAsString(signup))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auth-signup-social",
                        requestFields(
                                fieldWithPath("category").description("KAKAO")
                                        .attributes(key("constraint").value("소셜 로그인 구분")),
                                fieldWithPath("accessToken").description("카카오 Access Token")
                        )
                ));
    }


    @Test
    @DisplayName("SIGNIN")
    void signIn() throws Exception {
        User user = User.builder()
                .email("temp@gmail.com")
                .password("1234")
                .kakao(12345L)
                .build();
        userRepository.saveAndFlush(user);
        Optional<User> user2 = userRepository.findByKakao(12345L);
        when(kakaoService.getKakaoIdByToken("MOCKTOKEN")).thenReturn(12345L);
        when(kakaoService.getByKakao(12345L)).thenReturn(user2);
        SignIn signIn = SignIn.builder()
                .accessToken("MOCKTOKEN")
                .category("KAKAO")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signin")
                        .content(objectMapper.writeValueAsString(signIn))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("auth-kakao",
                        requestFields(
                                fieldWithPath("category").description("KAKAO")
                                        .attributes(key("constraint").value("소셜 로그인 구분")),
                                fieldWithPath("accessToken").description("카카오 Access Token")
                        )
                ));
    }
    @Test
    @DisplayName("SIGNIN FAIL")
    void signInFail() throws Exception {
        when(kakaoService.getKakaoIdByToken("MOCKTOKEN")).thenReturn(12345L);
        SignIn signIn = SignIn.builder()
                .accessToken("MOCKTOKEN")
                .category("KAKAO")
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signin")
                        .content(objectMapper.writeValueAsString(signIn))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(document("auth-kakao",
                        requestFields(
                                fieldWithPath("category").description("KAKAO")
                                        .attributes(key("constraint").value("소셜 로그인 구분")),
                                fieldWithPath("accessToken").description("카카오 Access Token")
                        )
                ));
    }

    @Test
    @DisplayName("POST(TEMP)")
    void tmp_test1() throws Exception {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);

        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{postId}", 1L)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-inquiry",
                        pathParameters(
                                parameterWithName("postId").description("게시글 ID")
                        )
                ));
    }
    @Test
    @HoopMockUser
    @DisplayName("POST CREATE(TEMP)")
    void tmp_test2() throws Exception {
        // given
        PostCreate request = PostCreate.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(RestDocumentationRequestBuilders.post("/posts")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-create",
                        requestFields(
                                fieldWithPath("title").description("제목")
                                        .attributes(key("constraint").value("좋은제목 입력해주세요.")),
                                fieldWithPath("content").description("내용").optional()
                        )
                ));
    }
}






