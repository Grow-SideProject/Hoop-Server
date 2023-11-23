package com.hoop.api.repository;

import com.hoop.api.domain.Game;
import com.hoop.api.domain.GameAttendant;
import com.hoop.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameAttendantRepository extends JpaRepository<GameAttendant, Long> {

    Optional<GameAttendant> deleteOneByUserAndGame(User user, Game game);

    Optional<GameAttendant> findByUserAndGame(User user, Game game);


}