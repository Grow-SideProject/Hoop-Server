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
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;
    private int height;
    private int weight;
    private String desc;
    private List<String> positions;
    private String name;

    private String profileImagePath;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public Profile(String name, int height, int weight, String desc, List<String> positions, User user) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.desc = desc;
        this.positions = positions;
        this.createdAt = LocalDateTime.now();
        this.user = user;
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
    public void setProfileImagePath(String path) {
        this.profileImagePath = path;
    }

}
