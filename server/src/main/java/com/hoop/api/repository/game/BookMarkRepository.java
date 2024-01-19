package com.hoop.api.repository.game;

import com.hoop.api.domain.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    List<BookMark> findByUserId(Long userId);
}