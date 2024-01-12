package com.hoop.api.repository;

import com.hoop.api.constant.AttendantStatus;
import com.hoop.api.domain.Attendant;
import com.hoop.api.domain.Game;
import com.hoop.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendantRepository extends JpaRepository<Attendant, Long> {


    Optional<Attendant> findByUserAndGame(User user, Game game);
    Optional<Attendant> findByUserIdAndGameId(Long userId, Long gameId);
    Optional<Attendant> findByGameAndStatus(Game game, AttendantStatus status);

}