package com.hoop.api.constant;

public enum Level {

    BEGINNER("입문자"),
    ROOKIE("초보"),
    INTERMEDIATE("중수"),
    MASTER("고수");


    private final String value;

    private Level(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}