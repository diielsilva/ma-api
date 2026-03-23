package com.todev.ma.enrollment.order;

import com.todev.ma.shared.InternalApplicationException;

class MissingPixDetailException extends InternalApplicationException {
    private static final String EXCEPTION_MESSAGE = "Os detalhes do pedido de pagamento estão ausentes";

    MissingPixDetailException() {
        super(EXCEPTION_MESSAGE);
    }
}
