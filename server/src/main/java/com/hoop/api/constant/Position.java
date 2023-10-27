package com.hoop.api.constant;

public enum Position {

    POINT_GUARD("포인트 가드", 10),
    SHOOTING_GUARD("슈팅 가드", 20),
    POWER_FORWARD("파워 포워드", 30),
    SMALL_FORWARD("스몰 포워드", 40),
    CENTER("센터", 50);

    private final String name;
    private final Integer value;

    private Position(String name, Integer value) {
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