package com.hoop.api.controller;

import com.hoop.api.config.UserPrincipal;
import com.hoop.api.exception.FileNotFound;
import com.hoop.api.request.user.PhoneValidation;
import com.hoop.api.request.user.ProfileEdit;
import com.hoop.api.response.DefaultResponse;
import com.hoop.api.response.user.ProfileResponse;
import com.hoop.api.service.ImageService;
import com.hoop.api.service.user.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private  final ProfileService profileService;
    private  final ImageService imageService;

    @GetMapping("/phone-validation")
    public DefaultResponse validNumber(@RequestParam String phoneNumber) {
        profileService.validateNumber(phoneNumber);
        //TODO 난수 생성 후 -> 메세지 전송 로직 추가
        return new DefaultResponse();
    }
    @GetMapping("/name-validation")
    public DefaultResponse validName(@RequestParam String nickName) {
        profileService.validateName(nickName);
        return new DefaultResponse();
    }
    @PostMapping("/phone")
    public DefaultResponse verifyNumber(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid PhoneValidation phoneValidation) {
        profileService.savePhoneNumber(userPrincipal.getUserId(), phoneValidation);
        return new DefaultResponse();
    }


    @GetMapping()
    public ProfileResponse get(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return profileService.get(userPrincipal.getUserId());
    }

    @PostMapping()
    public DefaultResponse edit(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody @Valid ProfileEdit request) {
        profileService.edit(userPrincipal.getUserId(), request);
        return new DefaultResponse();
    }

    /* 프로필 이미지 업로드 기능 */
    @PostMapping("/image")
    public DefaultResponse uploadImage(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam("file") MultipartFile file) {
        Long userId = userPrincipal.getUserId();
        String path = imageService.saveImage(userId, "profile", file);
        profileService.saveImage(userId, imageService.getImagePath(path));
        return new DefaultResponse();
    }
    @GetMapping("/image")
    public String getImage(@AuthenticationPrincipal UserPrincipal userPrincipal){
        String profileImagePath = profileService.getProfileImagePath(userPrincipal.getUserId());
        if (profileImagePath == null) {
            throw new FileNotFound();
        }
        return profileImagePath;
    }

}