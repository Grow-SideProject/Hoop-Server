package com.hoop.api.service.user;

import com.hoop.api.constant.Ability;
import com.hoop.api.domain.ProfileEditor;
import com.hoop.api.domain.User;
import com.hoop.api.exception.AlreadyExistsUserException;
import com.hoop.api.exception.ProfileException;
import com.hoop.api.exception.UserNotFound;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.user.PhoneRequest;
import com.hoop.api.request.user.ProfileEdit;
import com.hoop.api.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;

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

    public String getProfileImagePath(Long userId) {
        User profile = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
        return profile.getProfileImagePath();
    }

    public void validateNumber(String phoneNumber) {
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        if (userOptional.isPresent()) {
            throw  new AlreadyExistsUserException();
        }

    }

    public void validateName(String phoneNumber) {
        Optional<User> userOptional = userRepository.findByNickName(phoneNumber);
        if (userOptional.isPresent()) {
            throw  new AlreadyExistsUserException();
        }
    }

    @Transactional
    public void savePhoneNumber(Long userId, PhoneRequest phoneRequest) {
        if (phoneRequest.getSmsNumber() == null) throw new ProfileException("인증번호가 잘못되었습니다");
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        user.setPhoneNumber(phoneRequest.getPhoneNumber());
        userRepository.save(user);
    }

}