package com.hoop.api.domain;

import com.hoop.api.constant.Position;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;
    private LocalDateTime createdAt;
    private Integer height;
    private Integer weight;
    @Column(name = "description")
    private String desc;

    private List<Position> positions;
    private String name;

    private String profileImagePath;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public Profile(String phoneNumber, String name, Integer height, Integer weight, String desc, List<Position> positions, User user) {
        this.phoneNumber = phoneNumber;
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
                .phoneNumber(phoneNumber)
                .name(name)
                .height(height)
                .weight(weight)
                .desc(desc)
                .positions(positions);
    }

    public void edit(ProfileEditor profileEditor) {
        phoneNumber = profileEditor.getPhoneNumber();
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
