package com.hoop.api.response;

import com.hoop.api.constant.Ability;
import com.hoop.api.constant.Gender;
import com.hoop.api.constant.Level;
import com.hoop.api.constant.PlayStyle;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 서비스 정책에 맞는 클래스
 */
@Data
public class ProfileResponse {

    private String phoneNumber;
    private String nickName;
    private Gender gender;
    private String address;
    private String desc;
    private String playStyle;
    private String level;
    private List<String> abilities = new ArrayList<>();

    @Builder
    public ProfileResponse(String phoneNumber, String nickName, Gender gender, String address,
                           String desc, PlayStyle playStyle, Level level, List<Ability> abilities) {
        this.phoneNumber = phoneNumber;
        this.nickName = nickName;
        this.gender = gender;
        this.address = address;
        this.desc = desc;
        if (playStyle != null ) this.playStyle = playStyle.getValue();
        if (level != null ) this.level = level.getValue();
        if (abilities!= null && !abilities.isEmpty()) {
            for ( Ability ability : abilities ) {
                this.abilities.add(ability.getValue());
            }
        }
    }
}
