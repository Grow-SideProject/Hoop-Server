package com.hoop.api.config.annotation;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // 1
@Retention(RetentionPolicy.RUNTIME) // 2
@Constraint(validatedBy = StartTimeValidator.class) // 3
public @interface StarTime {
    String message() default "시작 시간이 유효하지 않습니다."; // 4
    Class[] groups() default {};
    Class[] payload() default {};
}