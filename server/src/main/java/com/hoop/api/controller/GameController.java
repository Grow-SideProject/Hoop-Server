package com.hoop.api.controller;


import com.hoop.api.config.UserPrincipal;

import com.hoop.api.constant.Ability;
import com.hoop.api.constant.GameCategory;
import com.hoop.api.request.game.GameCreate;
import com.hoop.api.request.game.GameSearch;
import com.hoop.api.response.DefaultResponse;
import com.hoop.api.response.GameResponse;
import com.hoop.api.service.game.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @PostMapping("/list")
    public List<GameResponse> getList(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody GameSearch gameSearch) {
        return gameService.getList(gameSearch);
    }

    @GetMapping("/{gameId}")
    public GameResponse get(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long gameId) {
        return gameService.get(gameId);
    }

    @PostMapping()
    public DefaultResponse create(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody GameCreate gameCreate) {
        gameService.create(userPrincipal.getUserId(), gameCreate);
        return new DefaultResponse();
    }

    @GetMapping("/attend")
    public void attendGame(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam Long gameId, @RequestParam boolean ballFlag) {
        gameService.attendGame(userPrincipal.getUserId(), gameId, ballFlag);
    }

    @GetMapping("/exit")
    public void exitGame(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam Long gameId) {
        gameService.exitGame(userPrincipal.getUserId(), gameId);
    }


    @GetMapping("/page")
    public Page<GameResponse> getPage(@AuthenticationPrincipal UserPrincipal userPrincipal, Pageable pageable) {
        return gameService.getPage(pageable);
    }


    @GetMapping("/catergories")
    public HashMap<String, String> getCatergories(){
        HashMap<String, String> response = new HashMap<String, String>();
        for (GameCategory gameCategory : GameCategory.values()) {
            response.put(gameCategory.name(), gameCategory.getValue());
        }
        return response;
    }
}
