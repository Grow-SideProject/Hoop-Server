package com.hoop.api.constant;

public enum Ability {

    PASS("패스"),
    DRIBBLE("드리블"),
    DRIVE_IN("돌파"),
    ACTIVITY("활동량"),
    PHYSICAL("피지컬"),

    DEFENSE("수비"),
    SHOOT("슛"),

    SIGHT("시야");


    public static final int MAX_COUNT = 3;
    private final String value;

    private Ability(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}