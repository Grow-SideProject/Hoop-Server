package com.hoop.api.request.game;

import com.hoop.api.config.annotation.StarTime;
import com.hoop.api.constant.GameCategory;
import com.hoop.api.constant.Gender;
import com.hoop.api.constant.Level;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@Getter
//@ToString
public class GameEdit {

    private Long gameId;

    private String title; // 모집 공고명
    private String content; // 모집 내용

    private String courtName; //코트명


    private String address; // 코트 주소 (추후 분리 예정)
    private Double xLoc; // 코트 x좌표
    private Double yLoc; // 코트 y좌표

    private GameCategory gameCategory; // 게임 종류


    @StarTime
    private String startTime;

    private Integer duration;

    private Boolean isBallFlag;

    private Gender gender; // 성별

    private Integer maxAttend; // 최대 인원

    private List<Level> levels = new ArrayList<>();

    private LocalDateTime modifiedAt;


    @Builder
    public GameEdit(String title, String content, String courtName,
                    String address, Double xLoc, Double yLoc,
                    GameCategory gameCategory, String startTime, Integer duration,
                    Boolean isBallFlag, Integer maxAttend, Gender gender, List<Level> levels) {
        this.title = title;
        this.content = content;
        this.courtName = courtName;
        //주소
        this.address = address;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.gameCategory = gameCategory;
        this.startTime = startTime;
        this.duration = duration;
        this.isBallFlag = isBallFlag;
        this.maxAttend = maxAttend;
        this.gender = gender;
        this.levels = levels;
        this.modifiedAt = LocalDateTime.now();
    }

    public LocalDateTime getStartTime() {
        return LocalDateTime.parse(this.startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
