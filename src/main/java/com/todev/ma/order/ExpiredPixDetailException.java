package com.todev.ma.order;

import com.todev.ma.shared.BusinessRuleViolationException;

class ExpiredPixDetailException extends BusinessRuleViolationException {
    private static final String EXCEPTION_MESSAGE = "Os detalhes do PIX estão expirados";

    ExpiredPixDetailException() {
        super(EXCEPTION_MESSAGE);
    }
}
