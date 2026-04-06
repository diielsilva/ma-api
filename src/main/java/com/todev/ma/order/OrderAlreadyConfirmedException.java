package com.todev.ma.order;

import com.todev.ma.shared.BusinessRuleViolationException;

class OrderAlreadyConfirmedException extends BusinessRuleViolationException {
    private static final String EXCEPTION_MESSAGE = "O usuário já está registrado no evento";

    OrderAlreadyConfirmedException() {
        super(EXCEPTION_MESSAGE);
    }
}
