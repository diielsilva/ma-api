package com.todev.ma.order;

class ConfirmedOrderException extends RuntimeException {
    private static final String EXCEPTION_MESSAGE = "O usuário já está registrado no evento";

    ConfirmedOrderException() {
        super(EXCEPTION_MESSAGE);
    }
}
