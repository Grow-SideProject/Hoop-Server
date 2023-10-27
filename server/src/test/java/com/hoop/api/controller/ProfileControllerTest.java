package com.hoop.api.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoop.api.config.HoopMockUser;
import com.hoop.api.config.UserPrincipal;
import com.hoop.api.constant.Position;
import com.hoop.api.domain.Profile;
import com.hoop.api.domain.User;
import com.hoop.api.repository.ProfileRepository;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.profile.ProfileCreate;
import com.hoop.api.request.profile.ProfileEdit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
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
        List<Position> myList = Arrays.asList(Position.CENTER, Position.POWER_FORWARD);
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
    @HoopMockUser
    @DisplayName("GET PROFILE")
    void test2() throws Exception {
        // given
        List<Position> myList = Arrays.asList(Position.CENTER, Position.POWER_FORWARD);
        ProfileCreate profileCreate = ProfileCreate.builder()
                .height(180)
                .weight(70)
                .desc("강한 타입")
                .positions(myList)
                .build();
        mockMvc.perform(post("/profile/create")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileCreate))
                )
                .andExpect(status().isOk())
                .andDo(print());

        // expected
        mockMvc.perform(get("/profile")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @HoopMockUser
    @DisplayName("EDIT PROFILE")
    void test3() throws Exception {
        // given
        List<Position> myList = Arrays.asList(Position.CENTER, Position.POWER_FORWARD);
        ProfileCreate profileCreate = ProfileCreate.builder()
                .height(180)
                .weight(70)
                .desc("강한 타입")
                .positions(myList)
                .build();
        mockMvc.perform(post("/profile/create")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileCreate))
                )
                .andExpect(status().isOk())
                .andDo(print());

        myList = Arrays.asList(Position.SMALL_FORWARD, Position.POWER_FORWARD);

        ProfileEdit profileEdit = ProfileEdit.builder()
                .height(200)
                .weight(100)
                .desc("매우 강한 타입")
                .positions(myList)
                .build();

        // expected
        mockMvc.perform(patch("/profile/edit")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileEdit)))
                .andExpect(status().isOk())
                .andDo(print());
    }



}










