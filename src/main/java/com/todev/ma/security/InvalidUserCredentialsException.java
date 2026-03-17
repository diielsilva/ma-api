package com.todev.ma.security;

class InvalidUserCredentialsException extends SecurityException {
    private static final String EXCEPTION_MESSAGE = "Email ou senha inválido (a)";

    InvalidUserCredentialsException() {
        super(EXCEPTION_MESSAGE);
    }
}
