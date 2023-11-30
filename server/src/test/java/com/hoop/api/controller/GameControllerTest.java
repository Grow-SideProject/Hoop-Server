package com.hoop.api.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoop.api.config.HoopMockUser;
import com.hoop.api.constant.GameCategory;
import com.hoop.api.domain.Game;
import com.hoop.api.domain.User;
import com.hoop.api.repository.GameAttendantRepository;
import com.hoop.api.repository.game.GameRepository;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.game.CommentCreate;
import com.hoop.api.request.game.GameCreate;
import com.hoop.api.service.user.JwtService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameAttendantRepository gameAttendantRepository;
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
        gameAttendantRepository.deleteAll();
        gameRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @HoopMockUser
    @DisplayName("CREATE GAME")
    void test1() throws Exception {
        // given
        GameCreate gameCreate = GameCreate.
                builder()
                .title("같이 농구합시다 3대3")
                .content("고수만 오셈")
                .address("마포구 서교동 12-1")
                .startTime("2021-10-10T10:00:00")
                .duration(120)
                .courtName("창천체육관")
                .maxAttend(Integer.valueOf(6))
                .gameCategory(GameCategory.THREE_ON_THREE)
                .isBallFlag(Boolean.TRUE)
                .build();

        String json = objectMapper.writeValueAsString(gameCreate);
        // expected
        mockMvc.perform(post("/game")
                        .header("Authorization",accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
        assertEquals(1, gameRepository.count());
        assertEquals(1, gameAttendantRepository.count());
    }

@Test
    @HoopMockUser
    @DisplayName("GET GAME")
    void test2() throws Exception {
        // given
        Game game = Game
                .builder()
                .title("같이 농구합시다 3대3")
                .content("고수만 오셈")
                .address("마포구 서교동 12-1")
                .startTime("2021-10-10T10:00:00")
                .duration(120)
                .courtName("창천체육관")
                .maxAttend(Integer.valueOf(6))
                .gameCategory(GameCategory.THREE_ON_THREE)
                .gameAttendants(new ArrayList<>())
                .build();

        gameRepository.save(game);
        // expected
        mockMvc.perform(get("/game")
                        .header("Authorization",accessToken)
                        .queryParam("page", String.valueOf(1))
                        .queryParam("size", String.valueOf(10))
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @HoopMockUser
    @DisplayName("ATTEND GAME")
    void test3() throws Exception {
        // given
        Game game = Game
                .builder()
                .title("같이 농구합시다 3대3")
                .content("고수만 오셈")
                .address("마포구 서교동 12-1")
                .startTime("2021-10-10T10:00:00")
                .duration(120)
                .courtName("창천체육관")
                .maxAttend(Integer.valueOf(6))
                .gameCategory(GameCategory.THREE_ON_THREE)
                .gameAttendants(new ArrayList<>())
                .build();

        gameRepository.save(game);
//        String json = objectMapper.writeValueAsString(gameAttend);
        // expected
        mockMvc.perform(get("/game/attend")
                        .header("Authorization",accessToken)
                        .contentType(APPLICATION_JSON)
                        .queryParam("gameId", game.getId().toString())
                        .queryParam("ballFlag", String.valueOf(true))
                )
                .andExpect(status().isOk())
                .andDo(print());
        assertEquals(1, gameAttendantRepository.count());
        // expected
        mockMvc.perform(get("/game")
                        .header("Authorization",accessToken)
                        .queryParam("page", String.valueOf(1))
                        .queryParam("size", String.valueOf(10))
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @HoopMockUser
    @DisplayName("EXIT GAME")
    void test4() throws Exception {
        // given
        Game game = Game
                .builder()
                .title("같이 농구합시다 3대3")
                .content("고수만 오셈")
                .address("마포구 서교동 12-1")
                .startTime("2021-10-10T10:00:00")
                .duration(120)
                .courtName("창천체육관")
                .maxAttend(Integer.valueOf(6))
                .gameCategory(GameCategory.THREE_ON_THREE)
                .gameAttendants(new ArrayList<>())
                .build();
        gameRepository.save(game);

        // expected
        mockMvc.perform(get("/game/attend")
                        .header("Authorization",accessToken)
                        .contentType(APPLICATION_JSON)
                        .queryParam("gameId", game.getId().toString())
                        .queryParam("ballFlag", String.valueOf(true))
                )
                .andExpect(status().isOk())
                .andDo(print());
        // expected
        mockMvc.perform(get("/game/exit")
                        .header("Authorization",accessToken)
                        .param("gameId", game.getId().toString())
                )
                .andExpect(status().isOk())
                .andDo(print());
        assertEquals(false, gameAttendantRepository.findAll().get(0).getAttend());

    }



    @Test
    @HoopMockUser
    @DisplayName("CREATE COMMENT")
    void test5() throws Exception {
        // given
        GameCreate gameCreate = GameCreate.
                builder()
                .title("같이 농구합시다 3대3")
                .content("고수만 오셈")
                .address("마포구 서교동 12-1")
                .startTime("2021-10-10T10:00:00")
                .duration(120)
                .courtName("창천체육관")
                .maxAttend(Integer.valueOf(6))
                .gameCategory(GameCategory.THREE_ON_THREE)
                .isBallFlag(Boolean.TRUE)
                .build();


        String json = objectMapper.writeValueAsString(gameCreate);
        // expected
        mockMvc.perform(post("/game")
                        .header("Authorization",accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        List<Game> game = gameRepository.findAll();
        Long gameId = game.get(0).getId();
        CommentCreate commentCreate = CommentCreate
                .builder()
                .author("작성자")
                .content("댓글입니다. 아아아아아 10글자 제한입니다.")
                .build();
        json = objectMapper.writeValueAsString(commentCreate);

        mockMvc.perform(post("/game/"+gameId+"/comments")
                        .header("Authorization",accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

}