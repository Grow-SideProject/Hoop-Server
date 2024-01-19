package com.hoop.api.service.user;

import com.hoop.api.domain.User;
import com.hoop.api.exception.AlreadyExistsUserException;
import com.hoop.api.exception.UserNotFound;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.user.SignUp;
import com.hoop.api.response.TokenResponse;
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
    private final JwtService jwtService;

    public void signup(SignUp signup) {
        userRepository.findBySocialId(signup.getSocialId())
                .ifPresent(user -> {
                    throw new AlreadyExistsUserException();
                });
        String encryptedPassword = passwordEncoder.encode(signup.getPassword());
        var user = User.builder()
                .email(signup.getEmail())
                .password(encryptedPassword)
                .socialId(signup.getSocialId())
                .refreshToken(signup.getRefreshToken())
                .build();
        userRepository.save(user);
    }

    @Transactional
    public void setRefreshTokenBySocialId(User user, String token) {
        user.setRefreshToken(token);
        userRepository.save(user);
    }


    @Transactional
    public TokenResponse signInBySocialId(Long socialId) {
        User user = userRepository.findBySocialId(socialId)
                .orElseThrow(UserNotFound::new);
        TokenResponse tokenResponse = jwtService.createTokenResponse(user.getEmail());
        user.setRefreshToken(tokenResponse.getRefreshToken());
        userRepository.save(user);
        return tokenResponse;
    }

}
