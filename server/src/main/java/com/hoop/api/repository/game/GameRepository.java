package com.hoop.api.repository.game;

import com.hoop.api.domain.Game;
import com.hoop.api.request.game.GameSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long>, GameRepositoryCustom {
    List<Game> getList(GameSearch gameSearch);

    @Override
    Page<Game> findAll(Pageable pageable);
}