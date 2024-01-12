package com.hoop.api.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.hoop.api.constant.GameCategory;
import com.hoop.api.constant.Gender;
import com.hoop.api.constant.Level;
import com.hoop.api.request.game.GameEdit;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title; // 모집 공고명
    private String content; // 모집 내용
    private String courtName; //코트명
    private String address; // 코트 주소 (추후 분리 예정)


    private LocalDateTime startTime;
    private Integer duration;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private Boolean isBallFlag;

    private Integer maxAttend; // 최대 인원

    private Gender gender; // 성별

    private GameCategory gameCategory; // 게임 종류
    @ElementCollection
    private List<Level> levels = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<Attendant> attendants;

    @Builder
    public Game(String title, String content, Integer maxAttend, String courtName, String address,
                String startTime, Integer duration, GameCategory gameCategory,Gender gender,
                Boolean isBallFlag, List<Level> levels, LocalDateTime modifiedAt) {
        this.title = title;
        this.content = content;
        this.courtName = courtName;
        this.address = address;
        this.gameCategory = gameCategory;
        this.startTime = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.duration = duration;
        this.gender = gender;
        this.isBallFlag = isBallFlag;
        this.maxAttend = maxAttend;
        this.createdAt = LocalDateTime.now();
        this.comments = new ArrayList<>();
        this.levels = levels;
        this.attendants = new ArrayList<>();
    }

    public void GameEdit(GameEdit gameEdit) {
        this.modifiedAt = LocalDateTime.now();
        if (gameEdit.getTitle() != null) this.title = gameEdit.getTitle();
        if (gameEdit.getContent() != null) this.content = gameEdit.getContent();
        if (gameEdit.getCourtName() != null) this.courtName = gameEdit.getCourtName();
        if (gameEdit.getAddress() != null) this.address = gameEdit.getAddress();
        if (gameEdit.getGameCategory() != null) this.gameCategory = gameEdit.getGameCategory();
        if (gameEdit.getStartTime() != null) this.startTime = LocalDateTime.parse(gameEdit.getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (gameEdit.getDuration() != null) this.duration = gameEdit.getDuration();
        if (gameEdit.getIsBallFlag() != null) this.isBallFlag = gameEdit.getIsBallFlag();
        if (gameEdit.getMaxAttend() != null) this.maxAttend = gameEdit.getMaxAttend();
        if (gameEdit.getGender() != null) this.gender = gameEdit.getGender();
        if (gameEdit.getLevels() != null) this.levels = gameEdit.getLevels();
    }


}