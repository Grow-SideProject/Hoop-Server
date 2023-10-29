package com.hoop.api.controller;

import com.hoop.api.config.UserPrincipal;
import com.hoop.api.request.FileDto;
import com.hoop.api.request.profile.ProfileCreate;
import com.hoop.api.request.profile.ProfileEdit;
import com.hoop.api.response.ProfileResponse;
import com.hoop.api.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private  final ProfileService profileService;

    @PostMapping("/create")
    public void create(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody ProfileCreate request) {
        profileService.create(userPrincipal.getUserId(), request);
    }


    @GetMapping()
    public ProfileResponse get(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return profileService.get(userPrincipal.getUserId());
    }
    @PatchMapping("/edit")
    public void edit(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody ProfileEdit request) {
        profileService.edit(userPrincipal.getUserId(), request);
    }


    /* 프로필 이미지 업로드 기능 */
    @PostMapping("/image")
    public void uploadCenter(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam("file") MultipartFile file) {
        Long userId = userPrincipal.getUserId();
        profileService.saveImage(userId, file);
    }


    @GetMapping("/image")
    public FileDto getFiles(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        FileDto fileDto = profileService.getImage(userPrincipal.getUserId());
        return fileDto;
    }
}
