package com.hoop.api.domain;

import com.hoop.api.constant.GameCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<GameAttendant> gameAttendants;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<Comment> comments;

    @Builder
    public Game(Long id, String title, String content, LocalDateTime createdAt, Integer maxAttend,
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
