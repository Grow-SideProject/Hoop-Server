package com.hoop.api.response.game;

import com.hoop.api.constant.AttendantStatus;
import com.hoop.api.constant.GameCategory;
import com.hoop.api.constant.Gender;
import com.hoop.api.constant.Level;
import com.hoop.api.domain.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 서비스 정책에 맞는 클래스
 */
@Data
@NoArgsConstructor
public class GameListResponse {

    private Long id;
    private String title; // 모집 공고명
    private String content; // 모집 내용
    private LocalDateTime createdAt;
    private Integer maxAttend; // 최대 인원
    private String courtName; //코트명
    private String address; // 코트 주소 (추후 분리 예정)
    private GameCategory gameCategory; // 게임 종류
    private String startTime;
    private Integer duration;

    private Gender gender; // 성별

    private List<Level> levels = new ArrayList<>();

    private Boolean isBallFlag; // 공 여부

    private Integer attendCount;

    // 생성자 오버로딩
    public GameListResponse(Game game) {
        this.id = game.getId();
        this.title = game.getTitle();
        this.content = game.getContent();
        this.courtName = game.getCourtName();
        this.address = game.getAddress();
        this.gameCategory = game.getGameCategory();
        this.startTime = game.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.duration = game.getDuration();
        this.gender = game.getGender();
        this.maxAttend = game.getMaxAttend();
        this.isBallFlag = game.getIsBallFlag();
        this.createdAt = game.getCreatedAt();
        this.levels = game.getLevels();
        this.attendCount = game.getAttendants().stream().filter(gameAttendant -> gameAttendant.getStatus().equals(AttendantStatus.APPROVE)).toList().size();
    }
}
