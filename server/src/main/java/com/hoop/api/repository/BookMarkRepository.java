package com.hoop.api.repository;

import com.hoop.api.domain.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

}