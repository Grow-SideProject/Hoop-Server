package com.hoop.api.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.hoop.api.constant.GameCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Matching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title; // 모집 공고명
    private String contents; // 모집 내용
    private LocalDateTime createdAt;
    private Integer maxAttend; // 최대 인원
    private String courtName; //코트명
    private String address; // 코트 주소 (추후 분리 예정)
    private GameCategory gameCategory; // 게임 종류

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matching")
    private List<MatchingAttend> matchingAttends;

    @Builder
    public Matching(Long id, String title, String contents, LocalDateTime createdAt, Integer maxAttend,
                    String courtName, String address, GameCategory gameCategory, LocalDateTime startTime,
                    LocalDateTime endTime, List<MatchingAttend> matchingAttends) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.maxAttend = maxAttend;
        this.courtName = courtName;
        this.address = address;
        this.gameCategory = gameCategory;
        this.startTime = startTime;
        this.endTime = endTime;
        this.matchingAttends = new ArrayList<>();
    }

    public void addMatchingAttend(MatchingAttend matchingAttend) {
        this.matchingAttends.add(matchingAttend);
    }
    public void popMatchingAttend(MatchingAttend matchingAttend) {
        this.matchingAttends.remove(matchingAttend);
    }
}
