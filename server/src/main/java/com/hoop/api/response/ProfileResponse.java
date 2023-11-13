package com.hoop.api.response;

import com.hoop.api.constant.Level;
import com.hoop.api.constant.Position;
import com.hoop.api.domain.Post;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * 서비스 정책에 맞는 클래스
 */
@Data
public class ProfileResponse {

    private String phoneNumber;
    private String nickName;
    private Integer height;
    private Integer weight;
    private String desc;
    private String position;
    private Level level;

    @Builder
    public ProfileResponse(String phoneNumber, String nickName, Integer height, Integer weight,
                           String desc, Position position, Level level) {
        this.phoneNumber = phoneNumber;
        this.nickName = nickName;
        this.height = height;
        this.weight = weight;
        this.desc = desc;
        this.position = position.getValue();
        this.level = level;
    }
}
