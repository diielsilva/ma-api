package com.todev.ma.order.pix;

import com.todev.ma.shared.ModelNotFoundException;

class PixDetailNotFoundException extends ModelNotFoundException {
    private static final String EXCEPTION_MESSAGE = "Os detalhes do PIX não foram encontrados";

    PixDetailNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }
}
