package com.todev.ma.enrollment.order;

import com.todev.ma.shared.ModelNotFoundException;

class OrderNotFoundException extends ModelNotFoundException {
    private static final String EXCEPTION_MESSAGE = "O pedido de inscrição solicitado não foi encontrado";

    OrderNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }
}
