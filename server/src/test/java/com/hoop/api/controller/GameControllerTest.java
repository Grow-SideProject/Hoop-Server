package com.hoop.api.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoop.api.config.HoopMockUser;
import com.hoop.api.constant.GameCategory;
import com.hoop.api.domain.Game;
import com.hoop.api.domain.User;
import com.hoop.api.repository.GameRepository;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.game.GameAttend;
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

import java.time.LocalDateTime;
import java.util.ArrayList;

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
    private GameRepository gameAttendRepository;
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
        gameRepository.deleteAll();
        gameAttendRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @HoopMockUser
    @DisplayName("CREATE MATCHING")
    void test1() throws Exception {
        // given
        GameCreate gameCreate = GameCreate.
                builder()
                .title("같이 농구합시다 3대3")
                .contents("고수만 오셈")
                .address("마포구 서교동 12-1")
                .startTime(LocalDateTime.parse("2021-10-10T10:00:00"))
                .endTime(LocalDateTime.parse("2021-10-10T12:00:00"))
                .courtName("창천체육관")
                .maxAttend(Integer.valueOf(6))
                .gameCategory(GameCategory.THREE_ON_THREE)
                .isBallFlag(Boolean.TRUE)
                .build();

        String json = objectMapper.writeValueAsString(gameCreate);
        // expected
        mockMvc.perform(post("/game/create")
                        .header("Authorization",accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
        assertEquals(1, gameRepository.count());
        assertEquals(1, gameAttendRepository.count());
    }

    @Test
    @HoopMockUser
    @DisplayName("GET MATCHING")
    void test2() throws Exception {
        // given
        Game game = Game
                .builder()
                .title("같이 농구합시다 3대3")
                .contents("고수만 오셈")
                .address("마포구 서교동 12-1")
                .startTime(LocalDateTime.parse("2021-10-10T10:00:00"))
                .endTime(LocalDateTime.parse("2021-10-10T12:00:00"))
                .courtName("창천체육관")
                .maxAttend(Integer.valueOf(6))
                .gameCategory(GameCategory.THREE_ON_THREE)
                .gameAttends(new ArrayList<>())
                .build();

        gameRepository.save(game);

        GameAttend gameAttend = GameAttend.
                builder()
                .game(game)
                .isHost(Boolean.FALSE)
                .isBallFlag(Boolean.TRUE)
                .build();

        String json = objectMapper.writeValueAsString(gameAttend);
        // expected
        mockMvc.perform(post("/game/attend")
                        .header("Authorization",accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
        assertEquals(1, gameAttendRepository.count());
        // expected
        mockMvc.perform(get("/game")
                        .header("Authorization",accessToken)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

}