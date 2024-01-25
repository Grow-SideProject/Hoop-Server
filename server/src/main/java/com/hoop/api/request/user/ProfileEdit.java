package com.hoop.api.request.user;

import com.hoop.api.constant.Ability;
import com.hoop.api.constant.Gender;
import com.hoop.api.constant.Level;
import com.hoop.api.constant.PlayStyle;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProfileEdit {
    private String phoneNumber;
    private String nickName;
    private Gender gender;
    private String address;
    private String desc;
    private PlayStyle playStyle;
    private String birth;
    private Level level;
    private List<Ability> abilities;


    @Builder
    public ProfileEdit(String phoneNumber, String nickName, Gender gender, String address,
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