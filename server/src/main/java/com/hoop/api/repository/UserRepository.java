package com.hoop.api.repository;

import com.hoop.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);
    Optional<User> findBySocialId(Long socialId);

    Optional<User> findByRefreshToken(String token);
}
