package com.hoop.api.constant;

public enum AttendantStatus {

    DEFAULT("미승인"),

    APPROVE("승인"),

    REJECT("승인거절"),
    EXIT("참석취소"),
    ETC("기타");

    private final String value;

    private AttendantStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}