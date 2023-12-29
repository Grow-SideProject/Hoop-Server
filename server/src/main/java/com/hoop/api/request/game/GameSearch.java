package com.hoop.api.request.game;

import com.hoop.api.constant.GameCategory;
import com.hoop.api.constant.Gender;
import com.hoop.api.constant.Level;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Builder
public class GameSearch {

    private static final Integer MAX_SIZE = 2000;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

    @Builder.Default
    private String orderBy = "createdAt";

    private List<String> startTimes;
    private List<GameCategory> gameCategories;
    private Gender gender;
    private List<Level> levels;


    public long getOffset() {
        return (long) (max(1, page) - 1) * min(size, MAX_SIZE);
    }
}