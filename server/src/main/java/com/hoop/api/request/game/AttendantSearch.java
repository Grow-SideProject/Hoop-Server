package com.hoop.api.request.game;

import com.hoop.api.constant.AttendantStatus;
import com.hoop.api.constant.GameCategory;
import com.hoop.api.constant.Gender;
import com.hoop.api.constant.Level;
import com.hoop.api.domain.Game;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

@Getter
@NoArgsConstructor
public class AttendantSearch {

    private static final Integer MAX_SIZE = 2000;
    private Integer page;
    private Integer size;
    private String orderBy = "createdAt";
    private AttendantStatus attendantStatus;

    @Builder
    public AttendantSearch(Integer page, Integer size, String orderBy, AttendantStatus attendantStatus) {
        this.page = page;
        this.size = size;
        this.orderBy = orderBy;
        this.attendantStatus = attendantStatus;
    }

    public void setAttendantStatus(AttendantStatus attendantStatus) {
        this.attendantStatus = attendantStatus;
    }

    public long getOffset() {
        return (long) (page) * min(size, MAX_SIZE);
    }
}