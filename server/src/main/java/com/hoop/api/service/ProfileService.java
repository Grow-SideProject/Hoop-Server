package com.hoop.api.service;

import com.hoop.api.domain.Profile;
import com.hoop.api.domain.ProfileEditor;
import com.hoop.api.exception.ProfileException;
import com.hoop.api.exception.UserNotFound;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.repository.ProfileRepository;
import com.hoop.api.request.profile.ProfileCreate;
import com.hoop.api.request.profile.ProfileEdit;
import com.hoop.api.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {


    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    private final Environment env;


    public ProfileResponse get(Long userId) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(UserNotFound::new);
        return ProfileResponse.builder()
                .phoneNumber(profile.getPhoneNumber())
                .nickName(profile.getNickName())
                .height(profile.getHeight())
                .weight(profile.getWeight())
                .desc(profile.getDesc())
                .position(profile.getPosition())
                .level(profile.getLevel())
                .build();
    }

    public void create(Long userId, ProfileCreate profileCreate) {
        try {
            var user = userRepository.findById(userId)
                    .orElseThrow(UserNotFound::new);
            Profile profile = Profile.builder()
                    .phoneNumber(profileCreate.getPhoneNumber())
                    .nickName(profileCreate.getNickName())
                    .birth(profileCreate.getBirth())
                    .height(profileCreate.getHeight())
                    .weight(profileCreate.getWeight())
                    .desc(profileCreate.getDesc())
                    .position(profileCreate.getPosition())
                    .level(profileCreate.getLevel())
                    .user(user)
                    .build();
            profileRepository.save(profile);
        } catch (Exception e) {
            throw new ProfileException();
        }

    }

    @Transactional
    public void edit(Long userId, ProfileEdit profileEdit) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(UserNotFound::new);
        ProfileEditor.ProfileEditorBuilder editorBuilder = profile.toEditor();
        ProfileEditor profileEditor = editorBuilder
                .phoneNumber(profileEdit.getPhoneNumber())
                .birth(profileEdit.getBirth())
                .nickName(profileEdit.getNickName())
                .height(profileEdit.getHeight())
                .weight(profileEdit.getWeight())
                .desc(profileEdit.getDesc())
                .position(profileEdit.getPosition())
                .level(profileEdit.getLevel())
                .build();
        profile.edit(profileEditor);
    }

    @Transactional
    public void saveImage(Long userId, String path) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(UserNotFound::new);
        profile.setProfileImagePath(path);
        profileRepository.save(profile);
    }

    public String getImage(Long userId) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(UserNotFound::new);
        log.info(profile.getProfileImagePath());
        return profile.getProfileImagePath();
    }
}