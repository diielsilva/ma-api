package com.todev.ma.order;

class OrderAlreadyConfirmedException extends RuntimeException {
    private static final String EXCEPTION_MESSAGE = "O usuário já está registrado no evento";

    OrderAlreadyConfirmedException() {
        super(EXCEPTION_MESSAGE);
    }
}
