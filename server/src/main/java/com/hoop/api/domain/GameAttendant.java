package com.hoop.api.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 연관관계 클래스
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class GameAttendant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; //복합키 ID

    @NotNull
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game; //복합키 ID
    private Boolean isBallFlag;
    private Boolean isHost;
    private LocalDateTime createdAt;
    private Boolean isAttend;


    // 생성자, getter 및 setter 등의 메서드 추가
    @Builder
    public GameAttendant(User user, Game game, Boolean isHost, Boolean isBallFlag, Boolean isAttend) {
        this.user = user;
        this.game = game;
        this.createdAt = LocalDateTime.now();
        this.isHost = isHost;
        this.isBallFlag = isBallFlag;
        this.isAttend = isAttend;
    }


    public void setBallFlag(Boolean ballFlag) {
        this.isBallFlag = ballFlag;
    }

    public void setHost(Boolean host) {
        this.isHost = host;
    }

    public void setAttend(Boolean attend) {
        this.isAttend = attend;
    }
    public Boolean getHost() {
        return this.isHost;
    }

    public Boolean getAttend() {
        return this.isAttend;
    }

}