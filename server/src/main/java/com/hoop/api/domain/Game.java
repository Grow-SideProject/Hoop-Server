package com.hoop.api.domain;

import com.hoop.api.constant.GameCategory;
import com.hoop.api.constant.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private String courtName; //코트명
    private String address; // 코트 주소 (추후 분리 예정)
    private GameCategory gameCategory; // 게임 종류

    private LocalDateTime startTime;
    private Integer duration;
    private LocalDateTime createdAt;

    private Boolean isBallFlag;

    private Integer maxAttend; // 최대 인원

    private Gender gender; // 성별



    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<GameAttendant> gameAttendants;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<Comment> comments;


    @Builder
    public Game(String title, String content, Integer maxAttend, String courtName, String address,
                String startTime, Integer duration, GameCategory gameCategory,Gender gender, Boolean isBallFlag) {
        this.title = title;
        this.content = content;
        this.courtName = courtName;
        this.address = address;
        this.gameCategory = gameCategory;
        this.startTime = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.duration = duration;
        this.gender = gender;
        this.maxAttend = maxAttend;
        this.isBallFlag = isBallFlag;
        this.createdAt = LocalDateTime.now();;
        this.gameAttendants = new ArrayList<>();
        this.comments = new ArrayList<>();
    }
}
