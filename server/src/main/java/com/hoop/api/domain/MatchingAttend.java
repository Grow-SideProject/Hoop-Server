package com.hoop.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 연관관계 클래스
 */
@Entity
@IdClass(MatchingAttendId.class)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class MatchingAttend {
    @Id
    @ManyToOne
    @JoinColumn
    private User user; //복합키 ID

    @Id
    @ManyToOne
    @JoinColumn
    private Matching matching; //복합키 ID

    private boolean ballFlag;
    private boolean isHost;

    private LocalDateTime createdAt;


    // 생성자, getter 및 setter 등의 메서드 추가

    public MatchingAttend(User user, Matching matching, boolean isHost, boolean ballFlag) {
        this.user = user;
        this.matching = matching;
        this.createdAt = LocalDateTime.now();
        this.isHost = isHost;
        this.ballFlag = ballFlag;

    }

}








