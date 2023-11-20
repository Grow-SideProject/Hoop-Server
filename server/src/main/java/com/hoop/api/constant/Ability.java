package com.hoop.api.constant;

public enum Ability {

    PASS("패스"),
    DEFENSE("수비력"),
    SHOOTING("슈팅"),
    DRIBBLE("드리블"),
    PHYSICAL("체력"),
    SIGHT("시야"),
    SHOOT("슛");

    public static final int MAX_COUNT = 3;
    private final String value;

    private Ability(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}