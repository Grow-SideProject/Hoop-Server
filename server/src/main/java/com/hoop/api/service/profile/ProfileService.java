package com.hoop.api.service.profile;

import com.hoop.api.domain.Post;
import com.hoop.api.domain.ProfileEditor;
import com.hoop.api.domain.User;
import com.hoop.api.exception.PostNotFound;
import com.hoop.api.exception.UserNotFound;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.post.PostEdit;
import com.hoop.api.request.profile.ProfileEdit;
import com.hoop.api.response.PostResponse;
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

    public ProfileResponse get(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFound::new);

        return ProfileResponse.builder()
                .name(user.getName())
                .height(user.getHeight())
                .weight(user.getWeight())
                .desc(user.getDesc())
                .positions(user.getPositions())
                .build();
    }
    @Transactional
    public void edit(Long id, ProfileEdit profileEdit) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFound::new);
        ProfileEditor.ProfileEditorBuilder editorBuilder = user.toEditor();
        ProfileEditor userEditor = editorBuilder
                .name(profileEdit.getName())
                .height(profileEdit.getHeight())
                .weight(profileEdit.getWeight())
                .desc(profileEdit.getDesc())
                .positions(profileEdit.getPositions())
                .build();
        user.edit(userEditor);
    }
}
