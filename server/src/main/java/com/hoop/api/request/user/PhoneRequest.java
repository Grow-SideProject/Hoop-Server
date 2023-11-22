package com.hoop.api.request.user;

import lombok.Builder;
import lombok.Data;

@Data
public class PhoneRequest {
    private String phoneNumber;
    private String smsNumber;
    @Builder
    public PhoneRequest(String phoneNumber, String smsNumber) {
        this.phoneNumber = phoneNumber;
        this.smsNumber = smsNumber;
    }
}
