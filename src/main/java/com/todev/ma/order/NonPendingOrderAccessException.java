package com.todev.ma.order;

import com.todev.ma.shared.BusinessRuleViolationException;

class NonPendingOrderAccessException extends BusinessRuleViolationException {
    private static final String EXCEPTION_MESSAGE = "";

    NonPendingOrderAccessException() {
        super(EXCEPTION_MESSAGE);
    }
}
