package com.hoop.api.domain;

import com.hoop.api.constant.Level;
import com.hoop.api.constant.Position;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nickName;

    @Column(unique = true)
    private String phoneNumber;

    private String birth;

    private LocalDateTime createdAt;
    private Integer height;
    private Integer weight;
    @Column(name = "description")
    private String desc;

    private Position position;
    private Level level;

    private String profileImagePath;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public Profile(String phoneNumber, String nickName, Integer height, Integer weight, String desc, String birth,
                   Position position, Level level, User user) {
        this.phoneNumber = phoneNumber;
        this.nickName = nickName;
        this.height = height;
        this.weight = weight;
        this.desc = desc;
        this.position = position;
        this.createdAt = LocalDateTime.now();
        this.user = user;
        this.birth = birth;
        this.level = level;
    }

    public ProfileEditor.ProfileEditorBuilder toEditor() {
        return ProfileEditor.builder()
                .phoneNumber(phoneNumber)
                .nickName(nickName)
                .height(height)
                .weight(weight)
                .desc(desc)
                .position(position)
                .birth(birth)
                .level(level);
    }

    public void edit(ProfileEditor profileEditor) {
        phoneNumber = profileEditor.getPhoneNumber();
        nickName = profileEditor.getNickName();
        height = profileEditor.getHeight();
        weight = profileEditor.getWeight();
        desc = profileEditor.getDesc();
        position = profileEditor.getPosition();
        birth = profileEditor.getBirth();
        level = profileEditor.getLevel();
    }
    public void setProfileImagePath(String path) {
        this.profileImagePath = path;
    }

}
