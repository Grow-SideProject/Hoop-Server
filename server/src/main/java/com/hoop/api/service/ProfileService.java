package com.hoop.api.service;

import com.hoop.api.domain.Profile;
import com.hoop.api.domain.ProfileEditor;
import com.hoop.api.exception.UserNotFound;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.repository.ProfileRepository;
import com.hoop.api.request.profile.ProfileCreate;
import com.hoop.api.request.post.PostCreate;
import com.hoop.api.request.profile.ProfileEdit;
import com.hoop.api.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {


    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public ProfileResponse get(Long userId) {
        Profile profile = profileRepository.findByUser_Id(userId)
                .orElseThrow(UserNotFound::new);
        return ProfileResponse.builder()
                .name(profile.getName())
                .height(profile.getHeight())
                .weight(profile.getWeight())
                .desc(profile.getDesc())
                .positions(profile.getPositions())
                .build();
    }

    public void create(Long userId, ProfileCreate profileCreate) {
        var user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
        Profile profile = Profile.builder()
                .name(profileCreate.getName())
                .height(profileCreate.getHeight())
                .weight(profileCreate.getWeight())
                .desc(profileCreate.getDesc())
                .positions(profileCreate.getPositions())
                .user(user)
                .build();
        profileRepository.save(profile);
    }

    @Transactional
    public void edit(Long userId, ProfileEdit profileEdit) {
        Profile profile = profileRepository.findByUser_Id(userId)
                .orElseThrow(UserNotFound::new);
        ProfileEditor.ProfileEditorBuilder editorBuilder = profile.toEditor();
        ProfileEditor profileEditor = editorBuilder
                .name(profileEdit.getName())
                .height(profileEdit.getHeight())
                .weight(profileEdit.getWeight())
                .desc(profileEdit.getDesc())
                .positions(profileEdit.getPositions())
                .build();
        profile.edit(profileEditor);
    }
}
