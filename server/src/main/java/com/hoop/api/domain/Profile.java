package com.hoop.api.domain;

import com.hoop.api.constant.Ability;
import com.hoop.api.constant.Level;
import com.hoop.api.constant.PlayStyle;
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
    @Column(unique = true)
    private String nickName;
    @Column(unique = true)
    private String phoneNumber;
    private String birth;
    
    private String gender;
    private String address;
    @Column(name = "description")
    private String desc;

    private List<Ability> abilities;
    private PlayStyle playStyle;
    private Level level;

    private String profileImagePath;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
    private LocalDateTime createdAt;

    @Builder
    public Profile(String phoneNumber, String nickName, String gender, String address, String desc, String birth,
                   PlayStyle playStyle, Level level, User user, List<Ability> abilities) {
        this.phoneNumber = phoneNumber;
        this.nickName = nickName;
        this.gender = gender;
        this.address = address;
        this.desc = desc;
        this.playStyle = playStyle;
        this.createdAt = LocalDateTime.now();
        this.user = user;
        this.birth = birth;
        this.level = level;
        this.abilities = abilities;
    }

    public ProfileEditor.ProfileEditorBuilder toEditor() {
        return ProfileEditor.builder()
                .phoneNumber(phoneNumber)
                .nickName(nickName)
                .gender(gender)
                .address(address)
                .desc(desc)
                .playStyle(playStyle)
                .birth(birth)
                .level(level)
                .abilities(abilities);
    }

    public void edit(ProfileEditor profileEditor) {
        phoneNumber = profileEditor.getPhoneNumber();
        nickName = profileEditor.getNickName();
        gender = profileEditor.getGender();
        address = profileEditor.getAddress();
        desc = profileEditor.getDesc();
        playStyle = profileEditor.getPlayStyle();
        birth = profileEditor.getBirth();
        level = profileEditor.getLevel();
        abilities = profileEditor.getAbilities();
    }
    public void setProfileImagePath(String path) {
        this.profileImagePath = path;
    }

}
