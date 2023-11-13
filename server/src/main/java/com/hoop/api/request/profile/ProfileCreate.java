package com.hoop.api.request.profile;

import com.hoop.api.constant.Level;
import com.hoop.api.constant.Position;
import lombok.Builder;
import lombok.Data;


import java.text.SimpleDateFormat;

@Data
public class ProfileCreate {

    private String phoneNumber;
    private Integer height;
    private Integer weight;
    private String desc;
    private Position position;
    private String nickName;
    private String birth;
    private Level level;



    @Builder
    public ProfileCreate(String phoneNumber, String nickName, Integer height, Integer weight,
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
