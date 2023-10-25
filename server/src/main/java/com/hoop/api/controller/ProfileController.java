package com.hoop.api.controller;

import com.hoop.api.config.UserPrincipal;
import com.hoop.api.request.profile.ProfileCreate;
import com.hoop.api.request.profile.ProfileEdit;
import com.hoop.api.response.ProfileResponse;
import com.hoop.api.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ProfileController {

    private  final ProfileService profileService;

    @GetMapping("/profile/{userId}")
    public ProfileResponse get(@PathVariable Long userId) {
        return profileService.get(userId);
    }
    @PatchMapping("/profile/{userId}")
    public void edit(@PathVariable Long userId, @RequestBody ProfileEdit request) {
        profileService.edit(userId, request);
    }

    @PostMapping("/profile/create")
    public void create(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody ProfileCreate request) {
        profileService.create(userPrincipal.getUserId(), request);
    }
}
