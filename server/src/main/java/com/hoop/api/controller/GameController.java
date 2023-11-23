package com.hoop.api.controller;


import com.hoop.api.config.UserPrincipal;

import com.hoop.api.request.game.GameAttend;
import com.hoop.api.request.game.GameCreate;
import com.hoop.api.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;


    @GetMapping()
    public String get() {
        return "temp";
    }

    @PostMapping("/create")
    public void create(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody GameCreate gameCreate) {
        gameService.create(userPrincipal.getUserId(), gameCreate);
    }

    @PostMapping("/attend")
    public void attendGame(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody GameAttend gameAttend) {
        gameService.attendGame(userPrincipal.getUserId(), gameAttend);
    }

    @DeleteMapping("/exit")
    public void exitGame(@AuthenticationPrincipal UserPrincipal userPrincipal, Long GameId) {
        gameService.exitGame(userPrincipal.getUserId(), GameId);
    }

}
