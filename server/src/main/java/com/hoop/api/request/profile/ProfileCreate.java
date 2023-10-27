package com.hoop.api.request.profile;

import com.hoop.api.constant.Position;
import lombok.Builder;
import lombok.Data;


import java.util.List;

@Data
public class ProfileCreate {
    private Integer height;
    private Integer weight;
    private String desc;
    private List<Position> positions;
    private String name;


    @Builder
    public ProfileCreate(String name, Integer height, Integer weight, String desc, List<Position> positions) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.desc = desc;
        this.positions = positions;
    }
}
