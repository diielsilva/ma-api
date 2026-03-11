package com.todev.ma.crypto;

import com.todev.ma.shared.InternalApplicationException;

class DecodingException extends InternalApplicationException {
    private static final String EXCEPTION_MESSAGE = "Não foi possível descriptografar a propriedade selecionada";

    DecodingException() {
        super(EXCEPTION_MESSAGE);
    }
}
