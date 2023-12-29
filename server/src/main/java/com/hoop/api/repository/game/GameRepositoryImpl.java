package com.hoop.api.repository.game;

import com.hoop.api.constant.GameCategory;
import com.hoop.api.constant.Gender;
import com.hoop.api.constant.Level;
import com.hoop.api.domain.Game;
import com.hoop.api.domain.QGameAttendant;
import com.hoop.api.request.game.GameSearch;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.hoop.api.domain.QGame.game;
import static com.hoop.api.domain.QGameAttendant.gameAttendant;
import static com.hoop.api.domain.QUser.user;

@RequiredArgsConstructor
public class GameRepositoryImpl implements GameRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Game> getList(GameSearch gameSearch) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(gameSearch);
        return jpaQueryFactory.selectFrom(game)
                .where(
                        inGameCategories(gameSearch.getGameCategories()),
                        eqGameGender(gameSearch.getGender()),
                        inGameLevels(gameSearch.getLevels())

                )
                // .join(game.gameAttendants, gameAttendant)
                // .join(gameAttendant.user, user)
                .limit(gameSearch.getSize())
                .offset(gameSearch.getOffset())
                .orderBy(orderSpecifiers)
                .fetch();
    }
    @Override
    public OrderSpecifier[] createOrderSpecifier(GameSearch gameSearch) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        if(Objects.isNull(gameSearch.getOrderBy()) || gameSearch.getOrderBy().equals("createdAt")){
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, game.createdAt));
        }else if(gameSearch.getOrderBy().equals("startTime")){
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, game.startTime));
        }else{
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, game.title));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }

    private BooleanExpression inGameCategories(List<GameCategory> gameCategories) {
        if (gameCategories == null ||gameCategories.isEmpty()) {
            return null;
        }
        return game.gameCategory.in(gameCategories);
    }

    private BooleanExpression eqGameGender(Gender gender) {
        if (gender == null) return null;
        return game.gender.eq(gender);
    }

    private BooleanBuilder inGameLevels(List<Level> levels) {
        if (levels == null || levels.isEmpty()) {
            return null;
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (Level level : levels) {
            booleanBuilder.or(game.level.contains(level));
        }
        return booleanBuilder;
    }
}
