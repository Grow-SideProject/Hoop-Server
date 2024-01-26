package com.hoop.api.repository.attendant;

import com.hoop.api.constant.AttendantStatus;
import com.hoop.api.domain.Attendant;
import com.hoop.api.domain.QAttendant;
import com.hoop.api.request.game.AttendantSearch;
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
//                        eqUserId(attendantSearch.getUserId())
                        eqAttendantStatus(attendantSearch.getAttendantStatus())
                )
                .limit(attendantSearch.getSize())
                .offset(attendantSearch.getOffset())
                .fetch();
    }

    public List<Attendant> getListByHost(Long userId) {
        QAttendant attendant1 = new QAttendant("attendant1");
        QAttendant attendant2 = new QAttendant("attendant2");
        return jpaQueryFactory
                .select(attendant2)
                .from(attendant1)
                .where(
                        attendant1.user.id.eq(userId)
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