package com.hoop.api.request.game;

import com.hoop.api.config.annotation.StarTime;
import com.hoop.api.constant.GameCategory;
import com.hoop.api.constant.Gender;
import com.hoop.api.constant.Level;
import com.hoop.api.domain.Game;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
//@ToString
public class GameCreate {
    @NotBlank(message = "모집 타이틀을 입력하세요.")
    private String title; // 모집 공고명
    @NotBlank(message = "내용을 입력해주세요.")
    private String content; // 모집 내용

    @NotBlank(message = "코트명을 입력하세요.")
    private String courtName; //코트명
    @NotBlank(message = "주소를 입력하세요.")
    private String address; // 코트 주소 (추후 분리 예정)
    private GameCategory gameCategory; // 게임 종류

    @NotBlank(message = "시작 시간을 입력하세요.")
    @StarTime
    private String startTime;

    @Positive(message = "시간은 양수여야 합니다.")
    private Integer duration;

    private Boolean isBallFlag;

    private Gender gender; // 성별

    private Integer maxAttend; // 최대 인원

    private List<Level> levels = new ArrayList<>();

    @Builder
    public GameCreate(String title, String content, String courtName, String address,
                      GameCategory gameCategory, String startTime, Integer duration,
                      Boolean isBallFlag, Integer maxAttend, Gender gender, List<Level> levels) {
        this.title = title;
        this.content = content;
        this.courtName = courtName;
        this.address = address;
        this.gameCategory = gameCategory;
        this.startTime = startTime;
        this.duration = duration;
        this.isBallFlag = isBallFlag;
        this.maxAttend = maxAttend;
        this.gender = gender;
        this.levels = levels;

    }

    public Game toGame() {
        return Game.builder()
                .title(title)
                .content(content)
                .courtName(courtName)
                .address(address)
                .gameCategory(gameCategory)
                .startTime(startTime)
                .duration(duration)
                .isBallFlag(isBallFlag)
                .gender(gender)
                .maxAttend(maxAttend)
                .levels(levels)
                .build();
    }
}
