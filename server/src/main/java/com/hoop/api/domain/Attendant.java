package com.hoop.api.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.hoop.api.constant.AttendantStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 연관관계 클래스
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(
        indexes = {
                @Index(name = "IDX_ATTENDANT_GAME_ID", columnList = "game_id")
        }
)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Attendant {

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
    private AttendantStatus status;
    private LocalDateTime createdAt;


    // 생성자, getter 및 setter 등의 메서드 추가
    @Builder
    public Attendant(User user, Game game, Boolean isHost, Boolean isBallFlag, AttendantStatus status) {
        this.user = user;
        this.game = game;
        this.createdAt = LocalDateTime.now();
        this.status = status;
        this.isHost = isHost;
        this.isBallFlag = isBallFlag;
    }


    public void setBallFlag(Boolean ballFlag) {
        this.isBallFlag = ballFlag;
    }

    public void setHost(Boolean host) {
        this.isHost = host;
    }


    public void setAttend(AttendantStatus status) {
        this.status = status;
    }


}