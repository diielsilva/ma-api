package com.todev.ma.order;

import com.todev.ma.shared.BusinessRuleViolationException;

class PendingOrderException extends BusinessRuleViolationException {
    private static final String EXCEPTION_MESSAGE = "O usuário possui pedidos de inscrição pendentes";

    PendingOrderException() {
        super(EXCEPTION_MESSAGE);
    }
}
