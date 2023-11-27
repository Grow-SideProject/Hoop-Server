package com.hoop.api.repository.game;import com.hoop.api.domain.Game;
import com.hoop.api.request.game.GameSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hoop.api.domain.QGame.game;

@RequiredArgsConstructor
public class GameRepositoryImpl implements GameRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Game> getList(GameSearch gameSearch) {
        return jpaQueryFactory.selectFrom(game)
                .limit(gameSearch.getSize())
                .offset(gameSearch.getOffset())
                .orderBy(game.id.desc())
                .fetch();
    }
}
