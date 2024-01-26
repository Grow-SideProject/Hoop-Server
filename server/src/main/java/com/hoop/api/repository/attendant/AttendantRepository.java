package com.hoop.api.repository.attendant;

import com.hoop.api.domain.Attendant;
import com.hoop.api.request.game.AttendantSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendantRepository extends JpaRepository<Attendant, Long>, AttendantRepositoryCustom {
    Optional<Attendant> findByUserIdAndGameId(Long userId, Long gameId);

    Optional<Attendant> findByUserIdAndGameIdAndIsHost(Long userId, Long gameId, Boolean isHost);
    List<Attendant> getList(AttendantSearch attendantSearch);
    List<Attendant> getListByHost(Long userId);
}