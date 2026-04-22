package com.todev.ma.enrollment;

import com.todev.ma.shared.BusinessRuleViolationException;

class ChargeNotPaidException extends BusinessRuleViolationException {
    private static final String EXCEPTION_MESSAGE = "A inscrição não foi paga corretamente";

    ChargeNotPaidException() {
        super(EXCEPTION_MESSAGE);
    }
}
