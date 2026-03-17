package com.todev.ma.security;

class InvalidTokenException extends RuntimeException {
    private static final String EXCEPTION_MESSAGE = "O token fornecido é inválido, está expirado ou você não possui permissão para acessar este recurso";

    InvalidTokenException() {
        super(EXCEPTION_MESSAGE);
    }
}
