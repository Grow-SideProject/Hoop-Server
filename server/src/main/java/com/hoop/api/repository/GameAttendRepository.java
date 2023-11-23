package com.hoop.api.repository;

import com.hoop.api.domain.Game;
import com.hoop.api.domain.GameAttend;
import com.hoop.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameAttendRepository extends JpaRepository<GameAttend, Long> {

    Optional<GameAttend> deleteOneByUserAndGame(User user, Game game);

    Optional<GameAttend> findByUserAndGame(User user, Game game);
}