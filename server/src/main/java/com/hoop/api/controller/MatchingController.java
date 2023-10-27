package com.hoop.api.controller;


import com.hoop.api.config.UserPrincipal;

import com.hoop.api.request.matching.MatchingAttendRequest;
import com.hoop.api.request.matching.MatchingCreate;
import com.hoop.api.service.MatchingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/matching")
public class MatchingController {

    private final MatchingService matchingService;


    @GetMapping()
    public String get() {
        return "temp";
    }

    @PostMapping("/create")
    public void create(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody MatchingCreate matchingCreate) {
        matchingService.create(userPrincipal.getUserId(), matchingCreate);
    }

    @PostMapping("/attend")
    public void attendMatching(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody MatchingAttendRequest matchingAttendRequest) {
        matchingService.attendMatching(userPrincipal.getUserId(), matchingAttendRequest);
    }

    @DeleteMapping("/exit")
    public void exitMatching(@AuthenticationPrincipal UserPrincipal userPrincipal, Long MatchingId) {
        matchingService.exitMatching(userPrincipal.getUserId(), MatchingId);
    }

}
