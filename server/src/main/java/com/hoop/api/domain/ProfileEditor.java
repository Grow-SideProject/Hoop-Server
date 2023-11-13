package com.hoop.api.domain;

import com.hoop.api.constant.Level;
import com.hoop.api.constant.Position;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.List;

@Getter
public class ProfileEditor {

    private final String phoneNumber;
    private final String nickName;
    private final Integer height;
    private final Integer weight;
    private final String desc;
    private Position position;
    private String birth;
    private Level level;

    @Builder
    public ProfileEditor(String phoneNumber, String nickName, Integer height, Integer weight,
                         String desc, Position position, String birth, Level level) {
        this.phoneNumber = phoneNumber;
        this.nickName = nickName;
        this.height = height;
        this.weight = weight;
        this.desc = desc;
        this.position = position;
        this.birth = birth;
        this.level = level;

    }
}
