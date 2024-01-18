package com.hoop.api.config.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StartTimeValidator implements ConstraintValidator<StarTime, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startTime = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return now.isBefore(startTime);
        } catch (Exception e) {
            return false;
        }
    }
}