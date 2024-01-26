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
    // 기본 정보
    private String title; // 모집 공고명
    private String content; // 모집 내용
    private String courtName; //코트명
    private Boolean isBallFlag;

    //주소
    private String address; // 코트 주소 (추후 분리 예정)
    private Double xLoc; // 코트 x좌표
    private Double yLoc; // 코트 y좌표

    //시간
    private LocalDateTime startTime;
    private Integer duration;

    private Integer maxAttend; // 최대 인원
    private Gender gender; // 성별
    private GameCategory gameCategory; // 게임 종류
    @ElementCollection
    private List<Level> levels = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    //조회수
    private Integer views;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<BookMark> bookMarks;

    //댓글
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<Comment> comments;

    //참여자 정보
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<Attendant> attendants;

    @Builder
    public Game(String title, String content,String courtName, Integer maxAttend, Boolean isBallFlag,
                GameCategory gameCategory,Gender gender, List<Level> levels,
                String address, Double xLoc, Double yLoc,
                LocalDateTime startTime, Integer duration) {
        this.title = title;
        this.content = content;
        this.courtName = courtName;
        this.isBallFlag = isBallFlag;
        this.maxAttend = maxAttend;
        this.startTime = startTime;
        this.duration = duration;
        this.gender = gender;
        this.gameCategory = gameCategory;
        this.levels = levels;

        // 주소
        this.address = address;
        this.xLoc = xLoc;
        this.yLoc = yLoc;

        //초기화
        this.createdAt = LocalDateTime.now();
        this.comments = new ArrayList<>();
        this.attendants = new ArrayList<>();
        this.views = 0;
        this.bookMarks = new ArrayList<>();
    }

    public void GameEdit(GameEdit gameEdit) {
        this.modifiedAt = LocalDateTime.now();
        if (gameEdit.getTitle() != null) this.title = gameEdit.getTitle();
        if (gameEdit.getContent() != null) this.content = gameEdit.getContent();
        if (gameEdit.getCourtName() != null) this.courtName = gameEdit.getCourtName();
        if (gameEdit.getIsBallFlag() != null) this.isBallFlag = gameEdit.getIsBallFlag();
        if (gameEdit.getMaxAttend() != null) this.maxAttend = gameEdit.getMaxAttend();
        //주소
        if (gameEdit.getAddress() != null) this.address = gameEdit.getAddress();
        if (gameEdit.getXLoc() != null) this.xLoc = gameEdit.getXLoc();
        if (gameEdit.getYLoc() != null) this.yLoc = gameEdit.getYLoc();

        if (gameEdit.getGameCategory() != null) this.gameCategory = gameEdit.getGameCategory();
        if (gameEdit.getStartTime() != null) this.startTime = gameEdit.getStartTime();
        if (gameEdit.getDuration() != null) this.duration = gameEdit.getDuration();


        if (gameEdit.getGender() != null) this.gender = gameEdit.getGender();
        if (gameEdit.getLevels() != null) this.levels = gameEdit.getLevels();
    }


}