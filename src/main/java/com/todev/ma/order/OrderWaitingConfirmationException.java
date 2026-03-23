package com.todev.ma.order;

import com.todev.ma.shared.BusinessRuleViolationException;

class OrderWaitingConfirmationException extends BusinessRuleViolationException {
    private static final String EXCEPTION_MESSAGE = "O usuário possui pedidos de inscrição pendentes";

    OrderWaitingConfirmationException() {
        super(EXCEPTION_MESSAGE);
    }
}
