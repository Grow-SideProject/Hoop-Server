package com.hoop.api.constant;

public enum GameCategory {
    ONE_ON_ONE("1on1"),
    TWO_ON_TWO("2on2"),
    THREE_ON_THREE( "3on3"),

    FOUR_ON_FOUR("4on4"),

    FIVE_ON_FIVE( "5on5"),
    MAIN_COURT("공식경기"),

    FREE("자유경기");

    private final String value;

    private GameCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}