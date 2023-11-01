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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email; // 아이디

    private String password;


    private String refreshToken; // 임시로 db에 저장

    private LocalDateTime createdAt;

    private Long socialId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> posts;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private Profile profile;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<MatchingAttend> matchingAttends;

    @Builder
    public User(String email, String password, Long socialId) {
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.socialId = socialId;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
