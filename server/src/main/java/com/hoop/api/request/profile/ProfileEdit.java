package com.hoop.api.request.profile;

import com.hoop.api.constant.Level;
import com.hoop.api.constant.Position;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.List;

@Data
public class ProfileEdit {
    private String phoneNumber;
    private String nickName;
    private Integer height;
    private Integer weight;
    private String desc;
    private Position position;
    private String birth;
    private Level level;


    @Builder
    public ProfileEdit(String phoneNumber, String nickName, Integer height, Integer weight,
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