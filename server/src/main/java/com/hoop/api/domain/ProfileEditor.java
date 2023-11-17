package com.hoop.api.domain;

import com.hoop.api.constant.Ability;
import com.hoop.api.constant.Level;
import com.hoop.api.constant.PlayStyle;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ProfileEditor {

    private final String phoneNumber;
    private final String nickName;
    private final String gender;
    private final String address;
    private final String desc;
    private PlayStyle playStyle;
    private String birth;
    private Level level;
    private List<Ability> abilities;

    @Builder
    public ProfileEditor(String phoneNumber, String nickName, String gender, String address,
                         String desc, PlayStyle playStyle, String birth, Level level, List<Ability> abilities) {
        this.phoneNumber = phoneNumber;
        this.nickName = nickName;
        this.gender = gender;
        this.address = address;
        this.desc = desc;
        this.playStyle = playStyle;
        this.birth = birth;
        this.level = level;
        this.abilities = abilities;
    }
}
