package com.todev.ma.user;

import com.todev.ma.shared.DuplicatedDataException;

class DuplicatedCpfException extends DuplicatedDataException {
    private static final String EXCEPTION_MESSAGE = "O CPF fornecido já foi registrado";

    DuplicatedCpfException() {
        super(EXCEPTION_MESSAGE);
    }
}
