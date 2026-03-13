package com.todev.ma.user;

import com.todev.ma.shared.DuplicatedDataException;

class DuplicatedEmailException extends DuplicatedDataException {
    private static final String EXCEPTION_MESSAGE = "O email fornecido já foi registrado";

    DuplicatedEmailException() {
        super(EXCEPTION_MESSAGE);
    }
}
