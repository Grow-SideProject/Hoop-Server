package com.hoop.api.controller;

import com.hoop.api.config.UserPrincipal;
import com.hoop.api.constant.Ability;
import com.hoop.api.constant.Level;
import com.hoop.api.constant.PlayStyle;
import com.hoop.api.exception.FileNotFound;
import com.hoop.api.request.user.ProfileEdit;
import com.hoop.api.response.DefaultResponse;
import com.hoop.api.response.ProfileResponse;
import com.hoop.api.service.ImageService;
import com.hoop.api.service.user.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private  final ProfileService profileService;
    private  final ImageService imageService;

    @GetMapping()
    public ProfileResponse get(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return profileService.get(userPrincipal.getUserId());
    }

    @PostMapping()
    public DefaultResponse create(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody ProfileEdit request) {
        profileService.create(userPrincipal.getUserId(), request);
        return new DefaultResponse();
    }

    @PatchMapping()
    public DefaultResponse edit(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody ProfileEdit request) {
        profileService.edit(userPrincipal.getUserId(), request);
        return new DefaultResponse();
    }


    /* 프로필 이미지 업로드 기능 */
    @PostMapping("/image")
    public DefaultResponse uploadImage(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam("file") MultipartFile file) {
        Long userId = userPrincipal.getUserId();
        String path = imageService.saveImage(userId, "profile", file);
        profileService.saveImage(userId, path);
        return new DefaultResponse();
    }
    
    @GetMapping("/image")
    public String getImage(@AuthenticationPrincipal UserPrincipal userPrincipal){
        String relativePath = profileService.getImage(userPrincipal.getUserId());
        if (relativePath == null) {
            throw new FileNotFound();
        }
        String imgPath = imageService.getImage(relativePath);
        return imgPath;
    }

    @GetMapping("/playStyles")
    public HashMap<Integer, String>  getPlayStyle(){
        HashMap<Integer, String> response = new HashMap<Integer, String>();
        for (PlayStyle playStyle : PlayStyle.values()) {
            response.put(playStyle.ordinal(), playStyle.getValue());
        }
        return response;
    }

    @GetMapping("/abilities")
    public HashMap<Integer, String> getAbilities(){
        HashMap<Integer, String> response = new HashMap<Integer, String>();
        for (Ability ability : Ability.values()) {
            response.put(ability.ordinal(), ability.getValue());
        }
        return response;
    }

    @GetMapping("/levels")
    public HashMap<Integer, String> getLevels(){
        HashMap<Integer, String> response = new HashMap<Integer, String>();
        for (Level level : Level.values()) {
            response.put(level.ordinal(), level.getValue());
        }
        return response;
    }


}