package com.todev.ma.order;

import com.todev.ma.shared.ModelNotFoundException;

class OrderNotFoundException extends ModelNotFoundException {
    private static final String EXCEPTION_MESSAGE = "O pedido de inscrição solicitado não foi encontrado";

    OrderNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }
}
