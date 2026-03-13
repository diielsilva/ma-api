package com.todev.ma.user;

import com.todev.ma.shared.DuplicatedDataException;

class DuplicatedPhoneException extends DuplicatedDataException {
    private static final String EXCEPTION_MESSAGE = "O telefone fornecido já foi registrado";

    DuplicatedPhoneException() {
        super(EXCEPTION_MESSAGE);
    }
}
