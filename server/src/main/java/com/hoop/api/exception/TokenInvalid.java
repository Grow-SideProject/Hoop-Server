package com.hoop.api.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenInvalid extends AuthenticationException {

    public TokenInvalid(String msg, Throwable cause) {
        super(msg, cause);
    }

    public TokenInvalid(String msg) {
        super(msg);
    }
}