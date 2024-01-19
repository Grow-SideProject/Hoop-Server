package com.hoop.api.repository.game;

import com.hoop.api.constant.AttendantStatus;
import com.hoop.api.constant.GameCategory;
import com.hoop.api.constant.Gender;
import com.hoop.api.constant.Level;
import com.hoop.api.domain.Attendant;
import com.hoop.api.domain.Game;
import com.hoop.api.request.game.AttendantSearch;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import java.util.List;

import static com.hoop.api.domain.QAttendant.attendant;


@RequiredArgsConstructor
public class AttendantRepositoryImpl implements AttendantRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Attendant> getList(AttendantSearch attendantSearch) {
        return jpaQueryFactory.selectFrom(attendant)
                .where(
                        inGame(attendantSearch.getGameList())
                        ,eqAttendantStatus(attendantSearch.getAttendantStatus())
                )
                .limit(attendantSearch.getSize())
                .offset(attendantSearch.getOffset())
                .fetch();
    }

    private BooleanBuilder inGame(List<Game> games) {

        if (games == null || games.isEmpty()) {
            return null;
        }
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (Game game : games) {
            booleanBuilder.or(attendant.game.eq(game));
        }
        return booleanBuilder;
    }

    private BooleanExpression eqAttendantStatus(AttendantStatus attendantStatus) {
        if (attendantStatus == null) return null;
        return attendant.status.eq(attendantStatus);
    }
}
