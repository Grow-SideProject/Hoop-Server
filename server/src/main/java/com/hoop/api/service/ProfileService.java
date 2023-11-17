package com.hoop.api.service;

import com.hoop.api.constant.Ability;
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
                .gender(profile.getGender())
                .address(profile.getAddress())
                .desc(profile.getDesc())
                .playStyle(profile.getPlayStyle())
                .level(profile.getLevel())
                .abilities(profile.getAbilities())
                .build();
    }

    public void create(Long userId, ProfileCreate profileCreate) {
        try {
            var user = userRepository.findById(userId)
                    .orElseThrow(UserNotFound::new);
            if(profileCreate.getAbilities() != null && profileCreate.getAbilities().size() > Ability.MAX_COUNT) throw new ProfileException("능력치는 3개 이하로 설정해주세요.");
            Profile profile = Profile.builder()
                    .phoneNumber(profileCreate.getPhoneNumber())
                    .nickName(profileCreate.getNickName())
                    .birth(profileCreate.getBirth())
                    .gender(profileCreate.getGender())
                    .address(profileCreate.getAddress())
                    .desc(profileCreate.getDesc())
                    .playStyle(profileCreate.getPlayStyle())
                    .level(profileCreate.getLevel())
                    .user(user)
                    .abilities(profileCreate.getAbilities())
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
        if(profileEdit.getAbilities() != null && profileEdit.getAbilities().size() > Ability.MAX_COUNT) throw new ProfileException("능력치는 3개 이하로 설정해주세요.");
        ProfileEditor.ProfileEditorBuilder editorBuilder = profile.toEditor();
        ProfileEditor profileEditor = editorBuilder
                .phoneNumber(profileEdit.getPhoneNumber())
                .birth(profileEdit.getBirth())
                .nickName(profileEdit.getNickName())
                .gender(profileEdit.getGender())
                .address(profileEdit.getAddress())
                .desc(profileEdit.getDesc())
                .playStyle(profileEdit.getPlayStyle())
                .level(profileEdit.getLevel())
                .abilities(profileEdit.getAbilities())
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