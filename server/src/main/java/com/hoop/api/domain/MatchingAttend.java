package com.hoop.api.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class MatchingAttend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn
    private User user; //복합키 ID

    @NotNull
    @ManyToOne
    @JoinColumn
    private Matching matching; //복합키 ID

    private Boolean IsBallFlag;
    private Boolean isHost;

    private LocalDateTime createdAt;


    // 생성자, getter 및 setter 등의 메서드 추가

    @Builder
    public MatchingAttend(User user, Matching matching, Boolean isHost, Boolean IsBallFlag) {
        this.user = user;
        this.matching = matching;
        this.createdAt = LocalDateTime.now();
        this.isHost = isHost;
        this.IsBallFlag = IsBallFlag;
    }

}








