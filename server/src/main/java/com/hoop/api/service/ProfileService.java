package com.hoop.api.service;

import com.hoop.api.domain.Profile;
import com.hoop.api.domain.ProfileEditor;
import com.hoop.api.exception.FileUploadException;
import com.hoop.api.exception.UserNotFound;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.repository.ProfileRepository;
import com.hoop.api.request.FileDto;
import com.hoop.api.request.profile.ProfileCreate;
import com.hoop.api.request.profile.ProfileEdit;
import com.hoop.api.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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
        Profile profile = profileRepository.findByUserId(userId)
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

    @Transactional
    public void saveImage(Long userId, MultipartFile file) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(UserNotFound::new);
        try {
            String basicPath = System.getProperty("user.dir") + "/src/main/resources/static";
            if (!new File(basicPath).exists()) {
                new File(basicPath).mkdir();
            }
            String relPath = "/img/profile";
            String savePath = basicPath + relPath;
            if (!new File(savePath).exists()) {
                new File(savePath).mkdir();
            }
            String filename = "/"+ userId +"_"+ file.getOriginalFilename();
            String filePath = savePath + filename;
            file.transferTo(new File(filePath));
            profile.setProfileImagePath(relPath + filename);
            profileRepository.save(profile);
        } catch (Exception e) {
            throw new FileUploadException();
        }
    }

    public FileDto getImage(Long userId) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(UserNotFound::new);
        log.info(profile.getProfileImagePath());
        env.getProperty("spring.base-url");
        return FileDto.builder()
                .filePath(env.getProperty("spring.base-url") + profile.getProfileImagePath())
                .build();
    }
}
