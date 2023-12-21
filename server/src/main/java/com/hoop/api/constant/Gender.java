package com.hoop.api.constant;

public enum Gender {

    MALE("남자"),
    FEMALE("여자"),
    MIX("성별무관");

    private final String value;

    private Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}