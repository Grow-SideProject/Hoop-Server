package com.hoop.api.repository;

import com.hoop.api.domain.Attendant;
import com.hoop.api.domain.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    List<BookMark> findByUserId(Long userId);

    Optional<BookMark> findByUserIdAndGameId(Long userId, Long gameId);
}