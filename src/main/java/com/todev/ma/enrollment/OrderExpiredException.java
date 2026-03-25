package com.todev.ma.enrollment;

import com.todev.ma.shared.BusinessRuleViolationException;

class OrderExpiredException extends BusinessRuleViolationException {
    private static final String EXCEPTION_MESSAGE = "O pedido de inscrição selecionado expirou";

    OrderExpiredException() {
        super(EXCEPTION_MESSAGE);
    }
}
