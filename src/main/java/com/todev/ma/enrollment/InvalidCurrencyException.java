package com.todev.ma.enrollment;

import com.todev.ma.shared.BusinessRuleViolationException;

class InvalidCurrencyException extends BusinessRuleViolationException {
    private static final String EXCEPTION_MESSAGE = "O pagamento deve ser realizado em reais";

    InvalidCurrencyException() {
        super(EXCEPTION_MESSAGE);
    }
}
