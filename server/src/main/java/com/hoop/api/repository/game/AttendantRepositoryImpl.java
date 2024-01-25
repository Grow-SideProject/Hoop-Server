package com.hoop.api.repository.game;

import com.hoop.api.constant.AttendantStatus;
import com.hoop.api.constant.GameCategory;
import com.hoop.api.constant.Gender;
import com.hoop.api.constant.Level;
import com.hoop.api.domain.Attendant;
import com.hoop.api.domain.Game;
import com.hoop.api.domain.QAttendant;
import com.hoop.api.request.game.AttendantSearch;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import java.util.List;

import static com.hoop.api.domain.QAttendant.attendant;
import static com.hoop.api.domain.QGame.game;


@RequiredArgsConstructor
public class AttendantRepositoryImpl implements AttendantRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Attendant> getList(AttendantSearch attendantSearch) {
        return jpaQueryFactory.selectFrom(attendant)
                .where(
                        eqUserId(attendantSearch.getUserId())
                        ,eqAttendantStatus(attendantSearch.getAttendantStatus())
                )
                .limit(attendantSearch.getSize())
                .offset(attendantSearch.getOffset())
                .fetch();
    }

    public List<Attendant> getListByHost(AttendantSearch attendantSearch) {
        QAttendant attendant1 = new QAttendant("attendant1");
        QAttendant attendant2 = new QAttendant("attendant2");
        return jpaQueryFactory
                .select(attendant2)
                .from(attendant1)
                .where(
                        attendant1.user.id.eq(attendantSearch.getUserId())
                        ,attendant1.isHost.eq(true)
                )
                .rightJoin(attendant2).on(attendant1.game.id.eq(attendant2.game.id))
                .where(
                        attendant2.status.eq(AttendantStatus.DEFAULT)
                )
                .fetch();
    }
    private BooleanExpression eqAttendantStatus(AttendantStatus attendantStatus) {
        if (attendantStatus == null) return null;
        return attendant.status.eq(attendantStatus);
    }

    private BooleanExpression eqUserId(Long userId) {
        if (userId == null) return null;
        return attendant.user.id.eq(userId);
    }
}