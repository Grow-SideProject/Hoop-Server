package com.hoop.api.constant;

public enum Level {

    NEWBIE("입문자"),
    LEVEL_LOW("초보"),
    LEVEL_INTER("중수"),
    LEVEL_HIGH("고수");


    private final String value;

    private Level(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}