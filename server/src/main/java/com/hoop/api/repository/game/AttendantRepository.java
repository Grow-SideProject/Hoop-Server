package com.hoop.api.repository.game;

import com.hoop.api.constant.AttendantStatus;
import com.hoop.api.domain.Attendant;
import com.hoop.api.domain.Game;
import com.hoop.api.domain.User;
import com.hoop.api.request.game.AttendantSearch;
import com.hoop.api.request.game.GameSearch;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AttendantRepository extends JpaRepository<Attendant, Long>, AttendantRepositoryCustom {


    List<Attendant> findByUser(User user);
    Optional<Attendant> findByUserAndGame(User user, Game game);
    Optional<Attendant> findByUserIdAndGameId(Long userId, Long gameId);

    List<Attendant> getList(AttendantSearch attendantSearch);
    List<Attendant> getListByHost(Long userId);
}