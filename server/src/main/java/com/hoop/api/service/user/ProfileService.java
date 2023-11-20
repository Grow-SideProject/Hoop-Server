package com.hoop.api.service.user;

import com.hoop.api.constant.Ability;
import com.hoop.api.domain.ProfileEditor;
import com.hoop.api.domain.User;
import com.hoop.api.exception.ProfileException;
import com.hoop.api.exception.UserNotFound;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.user.ProfileEdit;
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

    private final Environment env;


    public ProfileResponse get(Long userId) {
        User profile = userRepository.findById(userId)
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

    @Transactional
    public void create(Long userId, ProfileEdit profileEdit) {
        User profile = userRepository.findById(userId)
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
    public void edit(Long userId, ProfileEdit profileEdit) {
        User profile = userRepository.findById(userId)
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
        User profile = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
        profile.setProfileImagePath(path);
        userRepository.save(profile);
    }

    public String getImage(Long userId) {
        User profile = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
        log.info(profile.getProfileImagePath());
        return profile.getProfileImagePath();
    }
}