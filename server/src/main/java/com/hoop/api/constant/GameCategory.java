package com.hoop.api.constant;

public enum GameCategory {
    ONE_ON_ONE("1on1", 10),

    TWO_ON_TWO("2on2", 20),
    THREE_ON_THREE("3on3", 30),

    FOUR_ON_FOUR("4on4", 40),

    FIVE_ON_FIVE("5on5", 50),
    MAIN_COURT("공식경기", 60),

    FREE("자유경기", 90);

    private final String name;
    private final Integer value;

    private GameCategory(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public Integer getValue() {
        return this.value;
    }
}