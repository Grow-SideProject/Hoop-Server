package com.hoop.api.service.auth;


import com.hoop.api.config.AppConfig;
import com.hoop.api.domain.User;
import com.hoop.api.exception.AlreadyExistsEmailException;
import com.hoop.api.repository.UserRepository;
import com.hoop.api.request.sign.Signup;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final AppConfig appConfig;

    public String createToken(String subject) {
        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());
        String token = Jwts.builder()
                .setSubject(subject)
                .signWith(key)
                .setIssuedAt(new Date())
                .compact();
        return token;
    }

}
