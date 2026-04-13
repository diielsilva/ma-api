package com.todev.ma.order;

import com.todev.ma.shared.BusinessRuleViolationException;

class UnauthorizedOrderAccessException extends BusinessRuleViolationException {
    private static final String EXCEPTION_MESSAGE = "O pedido solicitado pertence a outro usuário";

    UnauthorizedOrderAccessException() {
        super(EXCEPTION_MESSAGE);
    }
}
