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
import org.springframework.web.multipart.MultipartFile;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
@RequestMapping("/profile")
public class ProfileController {

    private  final ProfileService profileService;


    @PostMapping("/create")
    public void create(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody ProfileCreate request) {
        profileService.create(userPrincipal.getUserId(), request);
    }
}
