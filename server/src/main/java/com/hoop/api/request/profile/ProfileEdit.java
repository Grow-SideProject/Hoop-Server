package com.hoop.api.request.profile;

import com.hoop.api.constant.Position;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ProfileEdit {
    private String phoneNumber;
    private String name;
    private Integer height;
    private Integer weight;
    private String desc;
    private List<Position> positions;

    @Builder
    public ProfileEdit(String phoneNumber, String name, Integer height, Integer weight, String desc, List<Position> positions) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.desc = desc;
        this.positions = positions;
    }
}