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

    private LocalDateTime createdAt;

    private Long kakao;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> posts;

    //Profiles
    private Long height;
    private Long weight;
    private String desc;
    private List<String> positions;
    private String name;

    @Builder
    public User(String name, String email, String password, long kakao) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.kakao = kakao;
    }

    public ProfileEditor.ProfileEditorBuilder toEditor() {
        return ProfileEditor.builder()
                .name(name)
                .height(height)
                .weight(weight)
                .desc(desc)
                .positions(positions);
    }

    public void edit(ProfileEditor profileEditor) {
        name = profileEditor.getName();
        height = profileEditor.getHeight();
        weight = profileEditor.getWeight();
        desc = profileEditor.getDesc();
        positions = profileEditor.getPositions();
    }


}
