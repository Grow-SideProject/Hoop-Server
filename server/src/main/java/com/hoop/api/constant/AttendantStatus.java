package com.hoop.api.constant;

import lombok.Getter;

@Getter
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

}