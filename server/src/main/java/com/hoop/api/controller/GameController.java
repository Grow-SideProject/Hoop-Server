package com.hoop.api.controller;


import com.hoop.api.config.UserPrincipal;

import com.hoop.api.constant.GameCategory;
import com.hoop.api.request.game.*;
import com.hoop.api.response.DefaultResponse;
import com.hoop.api.response.AttendantResponse;
import com.hoop.api.response.GameDetailResponse;
import com.hoop.api.response.GameListResponse;
import com.hoop.api.service.game.CommentService;
import com.hoop.api.service.game.GameAdminService;
import com.hoop.api.service.game.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;
    private final GameAdminService gameAdminService;

    private final CommentService commentService;


    @PostMapping("/list")
    public Page<GameListResponse> getList(@RequestBody GameSearch gameSearch) {
        return gameService.getList(gameSearch);
    }

    @PostMapping()
    public DefaultResponse create(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid GameCreate gameCreate) {
        gameAdminService.create(userPrincipal.getUserId(), gameCreate);
        return new DefaultResponse();
    }

    @PatchMapping()
    public DefaultResponse edit(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody GameEdit gameEdit) {
        gameAdminService.edit(userPrincipal.getUserId(), gameEdit);
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
        return gameService.get(userPrincipal.getUserId(), gameId);
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
        return gameAdminService.approveGame(userPrincipal.getUserId(), gameId, userId);
    }
    @GetMapping("/reject")
    public AttendantResponse rejectGame(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam Long gameId, @RequestParam Long userId) {
        return gameAdminService.rejectGame(userPrincipal.getUserId(), gameId, userId);
    }
    @PostMapping("/comment")
    public void create(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid CommentCreate commentCreate) {;
        commentService.create(userPrincipal.getUserId(), commentCreate);
    }

    @PatchMapping("/comment")
    public void edit(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid CommentEdit commentEdit) {;
        commentService.edit(userPrincipal.getUserId(),commentEdit);
    }

    @DeleteMapping("/comment")
    public void delete(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid CommentDelete commentDelete) {;
        commentService.delete(userPrincipal.getUserId(),commentDelete);
    }
}
