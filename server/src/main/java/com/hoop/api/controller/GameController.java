package com.hoop.api.controller;


import com.hoop.api.config.UserPrincipal;

import com.hoop.api.constant.GameCategory;
import com.hoop.api.request.game.CommentCreate;
import com.hoop.api.request.game.GameCreate;
import com.hoop.api.request.game.GameSearch;
import com.hoop.api.response.DefaultResponse;
import com.hoop.api.response.AttendantResponse;
import com.hoop.api.response.GameDetailResponse;
import com.hoop.api.response.GameListResponse;
import com.hoop.api.service.game.CommentService;
import com.hoop.api.service.game.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    private final CommentService commentService;


    @PostMapping("/list")
    public Page<GameListResponse> getList(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody GameSearch gameSearch) {
        return gameService.getList(gameSearch);
    }

    @PostMapping()
    public DefaultResponse create(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody GameCreate gameCreate) {
        gameService.create(userPrincipal.getUserId(), gameCreate);
        return new DefaultResponse();
    }


    @GetMapping("/catergories")
    public HashMap<String, String> getCatergories(){
        HashMap<String, String> response = new HashMap<String, String>();
        for (GameCategory gameCategory : GameCategory.values()) {
            response.put(gameCategory.name(), gameCategory.getValue());
        }
        return response;
    }

    @GetMapping("/{gameId}")
    public GameDetailResponse get(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long gameId) {
        return gameService.get(gameId);
    }

    @GetMapping("/attend")
    public AttendantResponse attendGame(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam Long gameId, @RequestParam boolean ballFlag) {
        return gameService.attendGame(userPrincipal.getUserId(), gameId, ballFlag);
    }
    @GetMapping("/exit")
    public AttendantResponse exitGame(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam Long gameId) {
        return gameService.exitGame(userPrincipal.getUserId(), gameId);
    }

    @GetMapping("/approve")
    public AttendantResponse approveGame(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam Long gameId, @RequestParam Long userId) {
        return gameService.approveGame(userPrincipal.getUserId(), gameId, userId);
    }
    @GetMapping("/reject")
    public AttendantResponse rejectGame(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam Long gameId, @RequestParam Long userId) {
        return gameService.rejectGame(userPrincipal.getUserId(), gameId, userId);
    }
    @PostMapping("/{gameId}/comments")
    public void create(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long gameId, @RequestBody @Valid CommentCreate request) {;
        commentService.create(userPrincipal.getUserId(), gameId, request);
    }

}
