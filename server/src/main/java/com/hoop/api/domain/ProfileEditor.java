package com.hoop.api.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ProfileEditor {

    private final String name;
    private final int height;
    private final int weight;
    private final String desc;
    private List<String> positions;

    @Builder
    public ProfileEditor(String name, int height, int weight, String desc, List<String> positions) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.desc = desc;
        this.positions = positions;
    }
}
