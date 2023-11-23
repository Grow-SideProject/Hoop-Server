package com.hoop.api.constant;

public enum Level {

    BEGINNER("입문자"),
    ROOKIE("초보"),
    INTERMEDIATE("중수"),
    SEMIPRO("세미프로"),
    PRO("프로");

    private final String value;

    private Level(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}