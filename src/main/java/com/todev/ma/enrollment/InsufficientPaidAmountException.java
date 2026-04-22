package com.todev.ma.enrollment;

import com.todev.ma.shared.BusinessRuleViolationException;

class InsufficientPaidAmountException extends BusinessRuleViolationException {
    private static final String EXCEPTION_MESSAGE = "O valor pago é insuficiente";

    InsufficientPaidAmountException() {
        super(EXCEPTION_MESSAGE);
    }
}
