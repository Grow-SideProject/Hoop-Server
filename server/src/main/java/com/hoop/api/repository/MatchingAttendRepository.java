package com.hoop.api.repository;

import com.hoop.api.domain.Matching;
import com.hoop.api.domain.MatchingAttend;
import com.hoop.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchingAttendRepository extends JpaRepository<MatchingAttend, Long> {

    Optional<MatchingAttend> deleteOneByUserAndMatching(User user, Matching matching);

    Optional<MatchingAttend> findByUserAndMatching(User user, Matching matching);
}