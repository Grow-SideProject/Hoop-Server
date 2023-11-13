package com.hoop.api.constant;

public enum Position {

    POINT_GUARD("포인트 가드"),
    SHOOTING_GUARD( "슈팅 가드"),
    POWER_FORWARD("파워 포워드"),
    SMALL_FORWARD("스몰 포워드"),
    CENTER("센터");

    private final String value;

    private Position(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}