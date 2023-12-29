package com.hoop.api.repository.game;

import com.hoop.api.domain.Game;
import com.hoop.api.request.game.GameSearch;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GameRepositoryCustom {
    List<Game> getList(GameSearch gameSearch);

    OrderSpecifier[] createOrderSpecifier(GameSearch gameSearch);

}
