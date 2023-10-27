package com.hoop.api.request.matching;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hoop.api.constant.GameCategory;
import com.hoop.api.constant.Position;
import com.hoop.api.domain.Matching;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Data
//@ToString
public class MatchingCreate {
    private String title; // 모집 공고명
    private String contents; // 모집 내용
    private String courtName; //코트명
    private String address; // 코트 주소 (추후 분리 예정)
    private GameCategory gameCategory; // 게임 종류
    private LocalDateTime startTime;
    private LocalDateTime endTime;


    private Boolean isBallFlag;
    private Integer maxAttend; // 최대 인원

    @Builder
    public MatchingCreate(String title, String contents, String courtName, String address, GameCategory gameCategory, LocalDateTime startTime, LocalDateTime endTime, Boolean isBallFlag, Integer maxAttend) {
        this.title = title;
        this.contents = contents;
        this.courtName = courtName;
        this.address = address;
        this.gameCategory = gameCategory;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isBallFlag = isBallFlag;
        this.maxAttend = maxAttend;
    }

    public Matching toMatching() {
return Matching.builder()
                .title(title)
                .contents(contents)
                .courtName(courtName)
                .address(address)
                .gameCategory(gameCategory)
                .startTime(startTime)
                .endTime(endTime)
                .maxAttend(maxAttend)
                .build();
    }
}
