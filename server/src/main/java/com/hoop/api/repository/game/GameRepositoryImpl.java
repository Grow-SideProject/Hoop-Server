package com.hoop.api.repository.game;

import com.hoop.api.constant.GameCategory;
import com.hoop.api.constant.Gender;
import com.hoop.api.constant.Level;
import com.hoop.api.domain.Game;
import com.hoop.api.request.game.GameSearch;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.hoop.api.domain.QGame.game;

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
                        inGameLevels(gameSearch.getLevels()),
                        inStartTimes(gameSearch.getStartTimes())
                )
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


    private BooleanBuilder inStartTimes(List<String> startTimes) {

        if (startTimes == null || startTimes.isEmpty()) {
            return null;
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (String startTime : startTimes) {
            booleanBuilder.or(game.startTime.eq(LocalDateTime.parse(startTime,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        }
        return booleanBuilder;
    }

    private BooleanExpression eqGameGender(Gender gender) {
        if (gender == null || gender.equals(Gender.MIX)) return null;
        return game.gender.eq(gender);
    }

    private BooleanBuilder inGameLevels(List<Level> levels) {
        if (levels == null || levels.isEmpty()) {
            return null;
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (Level level : levels) {
            booleanBuilder.or(game.levels.contains(level));
        }
        return booleanBuilder;
    }

    private BooleanBuilder inGameCategories(List<GameCategory> gameCategories) {
        if (gameCategories == null || gameCategories.isEmpty()) {
            return null;
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (GameCategory gameCategory : gameCategories) {
            booleanBuilder.or(game.gameCategory.eq(gameCategory));
        }
        return booleanBuilder;
    }
}
