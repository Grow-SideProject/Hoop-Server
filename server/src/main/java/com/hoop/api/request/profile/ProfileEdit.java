package com.hoop.api.request.profile;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ProfileEdit {
    private String name;
    private Long height;
    private Long weight;
    private String desc;
    private List<String> positions;

    @Builder
    public ProfileEdit(String name, Long height, Long weight, String desc, List<String> positions) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.desc = desc;
        this.positions = positions;
    }
}