package com.hoop.api.response;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.hoop.api.constant.AttendantStatus;
import com.hoop.api.domain.Game;
import com.hoop.api.domain.GameAttendant;
import com.hoop.api.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 연관관계 클래스
 */
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class GameAttendantResponse {
    private Long id;
    private Boolean isBallFlag;
    private Boolean isHost;
    private AttendantStatus status;
    private LocalDateTime createdAt;
    private Long userId;
    private String userNickname;

    // 생성자, getter 및 setter 등의 메서드 추가

    public GameAttendantResponse(GameAttendant gameAttendant) {
        this.createdAt = gameAttendant.getCreatedAt();
        this.status = gameAttendant.getStatus();
        this.isHost = gameAttendant.getIsHost();
        this.userNickname = gameAttendant.getUser().getNickName();
        this.userId = gameAttendant.getUser().getId();
    }
    @Builder
    public GameAttendantResponse(Boolean isHost, AttendantStatus status, User user){
        this.createdAt = LocalDateTime.now();
        this.status = status;
        this.isHost = isHost;
        this.userNickname = user.getNickName();
        this.userId = user.getId();
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