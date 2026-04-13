package com.todev.ma.order;

import com.todev.ma.shared.BusinessRuleViolationException;

class OrderDoesNotHavePixDetailException extends BusinessRuleViolationException {
    private static final String EXCEPTION_MESSAGE = "O pedido solicitado não possui pagamento via PIX";

    OrderDoesNotHavePixDetailException() {
        super(EXCEPTION_MESSAGE);
    }
}
