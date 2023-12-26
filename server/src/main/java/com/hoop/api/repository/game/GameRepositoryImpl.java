package com.hoop.api.repository.game;

import com.hoop.api.domain.Game;
import com.hoop.api.request.game.GameSearch;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

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
}
