package com.hoop.api.service.auth;

import com.hoop.api.domain.User;
import com.hoop.api.exception.AlreadyExistsEmailException;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.sign.SignUp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
                .kakao(signup.getKakao())
                .build();
        userRepository.save(user);
    }


}
