package com.hoop.api.response;

import com.hoop.api.constant.GameCategory;
import com.hoop.api.domain.Game;
import com.hoop.api.domain.GameAttendant;
import com.hoop.api.domain.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 서비스 정책에 맞는 클래스
 */
@Getter
public class GameResponse {

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
    private LocalDateTime gameStartTime;
    private List<GameAttendant> gameAttendants;
    // 생성자 오버로딩
    public GameResponse(Game game) {
        this.id = game.getId();
        this.title = game.getTitle();
        this.content = game.getContent();
        this.createdAt = game.getCreatedAt();
        this.maxAttend = game.getMaxAttend();
        this.courtName = game.getCourtName();
        this.address = game.getAddress();
        this.gameCategory = game.getGameCategory();
        this.startTime = game.getStartTime();
        this.duration = game.getDuration();
        this.gameAttendants = new ArrayList<>();
    }

    @Builder
    public GameResponse(Long id, String title, String content, LocalDateTime createdAt, Integer maxAttend,
                String courtName, String address, GameCategory gameCategory, String startTime,
                Integer duration, List<GameAttendant> gameAttendants) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.maxAttend = maxAttend;
        this.courtName = courtName;
        this.address = address;
        this.gameCategory = gameCategory;
        this.startTime = startTime;
        this.duration = duration;
        this.gameAttendants = new ArrayList<>();
    }
}
