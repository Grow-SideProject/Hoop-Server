package com.hoop.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoop.api.config.AppConfig;
import com.hoop.api.config.HoopMockUser;
import com.hoop.api.domain.Post;
import com.hoop.api.repository.post.PostRepository;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.Signup;
import com.hoop.api.request.post.PostCreate;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
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

    @AfterEach
    void clean() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("SIGNUP")
    void test1() throws Exception {
        // given
        Signup signup = Signup.builder()
                .email("hodolman88@gmail.com")
                .password("1234")
                .name("호돌맨")
                .build();

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/signup")
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
                                fieldWithPath("kakao").description("kakao ID").optional(),
                                fieldWithPath("name").description("이름").optional()
                        )
                ));
    }

    @Test
    @DisplayName("KAKAO LOGIN")
    void test2() throws Exception {
        // expected
        mockMvc.perform(get("/auth/kakao?code=mockCode")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auth-kakao",
                        queryParameters(
                                parameterWithName("code").description("kakao 인증코드")
                        )
                ));
    }

    @Test
    @DisplayName("AUTH TEST")
    void test3() throws Exception {
        Long kakaoId = 1234L;
        Signup signup = Signup.builder()
                .email(Long.toString(kakaoId))
                .password(Long.toString(kakaoId))
                .name("카카오")
                .kakao(kakaoId)
                .build();
        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());
        String jws = Jwts.builder()
                .setSubject(Long.toString(kakaoId))
                .signWith(key)
                .setIssuedAt(new Date())
                .compact();
        mockMvc.perform(get("/helloworld/auth")
                        .header("Authorization",jws)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("auth-test",
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰 헤더")
                        )
                ));
    }

    @Test
    @DisplayName("(임시)글 단건 조회")
    void tmp_test1() throws Exception {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);

        // expected
        mockMvc.perform(get("/posts/{postId}", 1L)
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
    @DisplayName("(임시)글 등록")
    void tmp_test2() throws Exception {
        // given
        PostCreate request = PostCreate.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/posts")
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






