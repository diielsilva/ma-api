package com.todev.ma.crypto;

import com.todev.ma.shared.InternalApplicationException;

class EncodingException extends InternalApplicationException {
    private static final String EXCEPTION_MESSAGE = "Não foi possível criptografar a propriedade selecionada";

    EncodingException() {
        super(EXCEPTION_MESSAGE);
    }
}
