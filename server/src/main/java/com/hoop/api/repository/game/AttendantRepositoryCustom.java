package com.hoop.api.repository.game;

import com.hoop.api.domain.Attendant;
import com.hoop.api.domain.Game;
import com.hoop.api.request.game.AttendantSearch;
import com.hoop.api.request.game.GameSearch;
import com.querydsl.core.types.OrderSpecifier;

import java.util.List;

public interface AttendantRepositoryCustom {
    List<Attendant> getList(AttendantSearch attendantSearch);

    List<Attendant> getListByHost(AttendantSearch attendantSearch);

//    OrderSpecifier[] createOrderSpecifier(GameSearch gameSearch);

}
