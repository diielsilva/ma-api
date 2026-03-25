package com.todev.ma.enrollment;

import com.todev.ma.shared.BusinessRuleViolationException;

class InvalidOrderStatusException extends BusinessRuleViolationException {
    private static final String EXCEPTION_MESSAGE = "O pedido de inscrição selecionado já foi confirmado ou foi cancelado";

    InvalidOrderStatusException() {
        super(EXCEPTION_MESSAGE);
    }
}
