package com.hoop.api.controller;


import com.hoop.api.config.UserPrincipal;
import com.hoop.api.response.game.AttendantResponse;
import com.hoop.api.response.DefaultResponse;
import com.hoop.api.service.game.AttendantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/game/attendant")
public class AttendantController {

    private final AttendantService attendantService;

    @GetMapping()
    public List<AttendantResponse> getListByHost(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return attendantService.getListByHost(userPrincipal.getUserId());
    }

    @GetMapping("/approve")
    public AttendantResponse approve(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam Long attendantId) {
        return attendantService.approveAttendant(userPrincipal.getUserId(), attendantId);
    }
    @GetMapping("/reject")
    public AttendantResponse reject(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam Long attendantId) {
        return attendantService.rejectAttendant(userPrincipal.getUserId(), attendantId);
    }

    @GetMapping("/remove")
    public DefaultResponse remove(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam Long attendantId) {
        attendantService.removeGameAttend(userPrincipal.getUserId(), attendantId);
        return new DefaultResponse();
    }
}
