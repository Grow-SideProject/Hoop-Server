package com.hoop.api.repository.game;

import com.hoop.api.domain.Feedback;
import com.hoop.api.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}