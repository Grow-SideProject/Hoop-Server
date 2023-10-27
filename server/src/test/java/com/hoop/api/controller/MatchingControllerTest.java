package com.hoop.api.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoop.api.config.HoopMockUser;
import com.hoop.api.config.UserPrincipal;
import com.hoop.api.constant.GameCategory;
import com.hoop.api.constant.Position;
import com.hoop.api.domain.Matching;
import com.hoop.api.domain.Profile;
import com.hoop.api.domain.User;
import com.hoop.api.repository.MatchingRepository;
import com.hoop.api.repository.ProfileRepository;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.matching.MatchingAttendRequest;
import com.hoop.api.request.matching.MatchingCreate;
import com.hoop.api.request.profile.ProfileCreate;
import com.hoop.api.request.profile.ProfileEdit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MatchingControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MatchingRepository matchingRepository;

    @Autowired
    private MatchingRepository matchingAttendRepository;
    @AfterEach
    void clean() {
        matchingRepository.deleteAll();
        matchingAttendRepository.deleteAll();
    }

    @Test
    @HoopMockUser
    @DisplayName("CREATE MATCHING")
    void test1() throws Exception {
        // given
        MatchingCreate matchingCreate = MatchingCreate.
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

        String json = objectMapper.writeValueAsString(matchingCreate);
        // expected
        mockMvc.perform(post("/matching/create")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
        assertEquals(1, matchingRepository.count());
        assertEquals(1, matchingAttendRepository.count());
    }

    @Test
    @HoopMockUser
    @DisplayName("GET MATCHING")
    void test2() throws Exception {
        // given
        Matching matching = Matching
                .builder()
                .title("같이 농구합시다 3대3")
                .contents("고수만 오셈")
                .address("마포구 서교동 12-1")
                .startTime(LocalDateTime.parse("2021-10-10T10:00:00"))
                .endTime(LocalDateTime.parse("2021-10-10T12:00:00"))
                .courtName("창천체육관")
                .maxAttend(Integer.valueOf(6))
                .gameCategory(GameCategory.THREE_ON_THREE)
                .matchingAttends(new ArrayList<>())
                .build();

        matchingRepository.save(matching);

        MatchingAttendRequest matchingAttendRequest = MatchingAttendRequest.
                builder()
                .matching(matching)
                .isHost(Boolean.FALSE)
                .isBallFlag(Boolean.TRUE)
                .build();

        String json = objectMapper.writeValueAsString(matchingAttendRequest);
        // expected
        mockMvc.perform(post("/matching/attend")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
        assertEquals(1, matchingAttendRepository.count());
    }

}










