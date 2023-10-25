package com.hoop.api.request.profile;

import lombok.Builder;
import lombok.Data;


import java.util.List;

@Data
public class ProfileCreate {
    private int height;
    private int weight;
    private String desc;
    private List<String> positions;
    private String name;


    @Builder
    public ProfileCreate(String name, int height, int weight, String desc, List<String> positions) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.desc = desc;
        this.positions = positions;
    }
}
