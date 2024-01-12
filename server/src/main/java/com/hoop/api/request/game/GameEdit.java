package com.hoop.api.request.game;

import com.hoop.api.constant.GameCategory;
import com.hoop.api.constant.Gender;
import com.hoop.api.constant.Level;
import com.hoop.api.domain.Game;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
//@ToString
public class GameEdit {

    private Long id;
    private String title; // 모집 공고명
    private String content; // 모집 내용
    private String courtName; //코트명
    private String address; // 코트 주소 (추후 분리 예정)
    private GameCategory gameCategory; // 게임 종류
    private String startTime;

    private Integer duration;

    private Boolean isBallFlag;

    private Gender gender; // 성별

    private Integer maxAttend; // 최대 인원

    private List<Level> levels = new ArrayList<>();

    private LocalDateTime modifiedAt;


    @Builder
    public GameEdit(String title, String content, String courtName, String address,
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
        this.modifiedAt = LocalDateTime.now();
    }
}
