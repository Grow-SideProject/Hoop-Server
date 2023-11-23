package com.hoop.api.request.game;

import com.hoop.api.constant.GameCategory;
import com.hoop.api.domain.Game;
import lombok.*;

import java.time.LocalDateTime;


@Data
//@ToString
public class GameCreate {
    private String title; // 모집 공고명
    private String contents; // 모집 내용
    private String courtName; //코트명
    private String address; // 코트 주소 (추후 분리 예정)
    private GameCategory gameCategory; // 게임 종류
    private String startTime;
    private Integer duration;


    private Boolean isBallFlag;
    private Integer maxAttend; // 최대 인원

    @Builder
    public GameCreate(String title, String contents, String courtName, String address, GameCategory gameCategory, String startTime, Integer duration, Boolean isBallFlag, Integer maxAttend) {
        this.title = title;
        this.contents = contents;
        this.courtName = courtName;
        this.address = address;
        this.gameCategory = gameCategory;
        this.startTime = startTime;
        this.duration = duration;
        this.isBallFlag = isBallFlag;
        this.maxAttend = maxAttend;
    }

    public Game toGame() {
        return Game.builder()
                        .title(title)
                        .contents(contents)
                        .courtName(courtName)
                        .address(address)
                        .gameCategory(gameCategory)
                        .startTime(startTime)
                        .duration(duration)
                        .maxAttend(maxAttend)
                        .build();
    }
}
