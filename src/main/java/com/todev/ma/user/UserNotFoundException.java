package com.todev.ma.user;

import com.todev.ma.shared.ModelNotFoundException;

class UserNotFoundException extends ModelNotFoundException {
    private static final String EXCEPTION_MESSAGE = "O usuário solicitado não foi encontrado";

    UserNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }
}
