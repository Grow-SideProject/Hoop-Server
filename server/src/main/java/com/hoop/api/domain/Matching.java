package com.hoop.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Matching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 아이디

    private String contents;

    private LocalDateTime createdAt;

    private int maxAttend;

    private String locationName;
    private String address;
    private String gameCategory;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matching")
    private List<MatchingAttend> matchingAttends;



}
