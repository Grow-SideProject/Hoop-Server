package com.hoop.api.service.user;

import com.hoop.api.constant.Ability;
import com.hoop.api.domain.Feedback;
import com.hoop.api.domain.Game;
import com.hoop.api.domain.ProfileEditor;
import com.hoop.api.domain.User;
import com.hoop.api.exception.AlreadyExistsUserException;
import com.hoop.api.exception.ProfileException;
import com.hoop.api.exception.UserNotFound;
import com.hoop.api.exception.GameNotFound;
import com.hoop.api.repository.FeedbackRepository;
import com.hoop.api.repository.game.GameRepository;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.user.FeedbackRequest;
import com.hoop.api.request.user.PhoneRequest;
import com.hoop.api.request.user.ProfileEdit;
import com.hoop.api.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {


    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final FeedbackRepository feedbackRepository;
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

    public void validateNumber(String phoneNumber) {
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        if (userOptional.isPresent()) {
            throw  new AlreadyExistsUserException();
        }

    }

    public void validateName(String phoneNumber) {
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
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

    public void createFeedback(Long userId, FeedbackRequest feedbackRequest) {
        if ( userId == feedbackRequest.getTargetId()) throw new ProfileException("자기 자신에게 피드백을 남길 수 없습니다.");
        User user = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        User target = userRepository.findById(feedbackRequest.getTargetId()).orElseThrow(UserNotFound::new);
        Game game = gameRepository.findById(feedbackRequest.getGameId()).orElseThrow(GameNotFound::new);
        Feedback feedback = Feedback.builder()
                .user(user)
                .target(target)
                .game(game)
                .content(feedbackRequest.getContent())
                .score(feedbackRequest.getScore())
                .build();
        feedbackRepository.save(feedback);
    }
}