package com.hoop.api.repository;

import com.hoop.api.domain.Matching;
import com.hoop.api.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchingRepository extends JpaRepository<Matching, Long> {

}