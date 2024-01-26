package com.hoop.api.request.user;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PhoneValidation {
    @Pattern(regexp = "^[0-9]{3}-[0-9]{4}-[0-9]{4}$", message = "휴대폰 번호 양식이 올바르지 않습니다.(XXX-XXXX-XXXX)")
    private String phoneNumber;
    private String smsNumber;
    @Builder
    public PhoneValidation(String phoneNumber, String smsNumber) {
        this.phoneNumber = phoneNumber;
        this.smsNumber = smsNumber;
    }
}
