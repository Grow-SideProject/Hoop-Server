package com.hoop.api.request.game;

import com.hoop.api.constant.GameCategory;
import com.hoop.api.constant.Gender;
import com.hoop.api.constant.Level;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Data
@NoArgsConstructor
public class GameSearch {

    private static final Integer MAX_SIZE = 2000;
    private Integer page;
    private Integer size;
    private String orderBy = "createdAt";
    private List<String> startTimes;
    private List<GameCategory> gameCategories;
    private List<Level> levels;
    private Gender gender;

    @Builder
    public GameSearch(Integer page, Integer size, String orderBy, List<String> startTimes, List<GameCategory> gameCategories, List<Level> levels, Gender gender) {
        this.page = page;
        this.size = size;
        this.orderBy = orderBy;
        this.startTimes = startTimes;
        this.gameCategories = gameCategories;
        this.levels = levels;
        this.gender = gender;
    }

    public long getOffset() {
        return (long) (page + 1) * min(size, MAX_SIZE);
    }
}