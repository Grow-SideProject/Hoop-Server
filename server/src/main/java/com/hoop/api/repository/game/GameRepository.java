package com.hoop.api.repository.game;

import com.hoop.api.domain.Game;
import com.hoop.api.request.game.GameSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long>, GameRepositoryCustom {


    Optional<Game> getMyGameAfterNowAndIsOpened(Long userId);
    List<Game> getList(GameSearch gameSearch);

}