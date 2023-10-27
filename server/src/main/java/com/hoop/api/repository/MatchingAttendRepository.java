package com.hoop.api.repository;

import com.hoop.api.domain.Matching;
import com.hoop.api.domain.MatchingAttendId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingAttendRepository extends JpaRepository<Matching, MatchingAttendId> {

}