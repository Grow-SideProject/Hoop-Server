package com.hoop.api.controller;

import com.hoop.api.request.post.PostEdit;
import com.hoop.api.request.profile.ProfileEdit;
import com.hoop.api.response.PostResponse;
import com.hoop.api.response.ProfileResponse;
import com.hoop.api.service.profile.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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

}
