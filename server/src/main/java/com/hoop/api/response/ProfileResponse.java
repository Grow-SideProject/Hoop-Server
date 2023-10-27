package com.hoop.api.response;

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

    private String name;
    private Integer height;
    private Integer weight;
    private String desc;
    private List<Position> positions;

    @Builder
    public ProfileResponse(String name, Integer height, Integer weight, String desc, List<Position> positions) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.desc = desc;
        this.positions = positions;
    }
}
