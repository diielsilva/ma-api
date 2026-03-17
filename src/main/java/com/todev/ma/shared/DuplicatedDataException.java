package com.todev.ma.shared;

public abstract class DuplicatedDataException extends RuntimeException {
    protected DuplicatedDataException(String message) {
        super(message);
    }
}
