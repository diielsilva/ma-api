package com.todev.ma.crypto;

import com.todev.ma.shared.InternalApplicationException;

class CipherCreationException extends InternalApplicationException {
    private static final String EXCEPTION_MESSAGE = "Não foi possível criar a cifra de criptografia";

    CipherCreationException() {
        super(EXCEPTION_MESSAGE);
    }
}
