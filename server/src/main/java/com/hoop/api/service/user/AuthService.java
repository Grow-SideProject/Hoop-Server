package com.hoop.api.service.user;

import com.hoop.api.domain.User;
import com.hoop.api.exception.AlreadyExistsEmailException;
import com.hoop.api.exception.UserNotFound;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.user.SignUp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignUp signup) {
        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }
        String encryptedPassword = passwordEncoder.encode(signup.getPassword());
        var user = User.builder()
                .email(signup.getEmail())
                .password(encryptedPassword)
                .socialId(signup.getSocialId())
                .build();
        userRepository.save(user);
    }

    @Transactional
    public void setRefreshTokenBySocialId(Long socialId, String token) {
        User user = userRepository.findBySocialId(socialId).orElseThrow(UserNotFound::new);
        user.setRefreshToken(token);
        userRepository.save(user);
    }
}
