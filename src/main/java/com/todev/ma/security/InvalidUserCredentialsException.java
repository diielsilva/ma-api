package com.todev.ma.security;

import com.todev.ma.shared.SecurityException;

class InvalidUserCredentialsException extends SecurityException {
    private static final String EXCEPTION_MESSAGE = "Email ou senha inválido (a)";

    InvalidUserCredentialsException() {
        super(EXCEPTION_MESSAGE);
    }
}
