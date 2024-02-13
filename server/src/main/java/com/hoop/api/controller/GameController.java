package com.hoop.api.controller;


import com.hoop.api.config.UserPrincipal;

import com.hoop.api.request.game.*;
import com.hoop.api.response.game.AttendantResponse;
import com.hoop.api.response.DefaultResponse;
import com.hoop.api.response.game.GameDetailResponse;
import com.hoop.api.response.game.GameListResponse;
import com.hoop.api.service.game.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    @PostMapping("/list")
    public Page<GameListResponse> getList(@RequestBody GameSearch gameSearch) {
        return gameService.getList(gameSearch);
    }

    @PostMapping()
    public DefaultResponse create(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid GameCreate gameCreate) {
        gameService.create(userPrincipal.getUserId(), gameCreate);
        return new DefaultResponse();
    }

    @PatchMapping()
    public DefaultResponse edit(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid GameEdit gameEdit) {
        gameService.edit(userPrincipal.getUserId(), gameEdit);
        return new DefaultResponse();
    }

    @GetMapping("/{gameId}")
    public GameDetailResponse get(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long gameId) {
        return gameService.getById(userPrincipal.getUserId(), gameId);
    }

    @GetMapping("/attend")
    public AttendantResponse attendGame(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam Long gameId) {
        return gameService.attendGame(userPrincipal.getUserId(), gameId);
    }

    @GetMapping("/exit")
    public AttendantResponse exitGame(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam Long gameId) {
        return gameService.exitGame(userPrincipal.getUserId(), gameId);
    }

    @DeleteMapping("/{gameId}")
    public void delete(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long gameId) {
        gameService.delete(userPrincipal.getUserId(), gameId);
    }

    @GetMapping("/my-game")
    public GameDetailResponse getMyGame(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return gameService.getMyGame(userPrincipal.getUserId());
    }
}
