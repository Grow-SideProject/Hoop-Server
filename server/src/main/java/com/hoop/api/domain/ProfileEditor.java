package com.hoop.api.domain;

import com.hoop.api.constant.Position;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ProfileEditor {

    private final String name;
    private final Integer height;
    private final Integer weight;
    private final String desc;
    private List<Position> positions;

    @Builder
    public ProfileEditor(String name, Integer height, Integer weight, String desc, List<Position> positions) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.desc = desc;
        this.positions = positions;
    }
}
