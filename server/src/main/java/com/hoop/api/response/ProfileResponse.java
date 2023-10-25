package com.hoop.api.response;

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
    private int height;
    private int weight;
    private String desc;
    private List<String> positions;

    @Builder
    public ProfileResponse(String name, int height, int weight, String desc, List<String> positions) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.desc = desc;
        this.positions = positions;
    }
}
