package com.hoop.api.constant;

public enum PlayStyle {

    AGGRESSIVE("공격적"),
    BALANCE("밸런스"),
    DEFENSIVE("방어적");

    private final String value;

    private PlayStyle(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}