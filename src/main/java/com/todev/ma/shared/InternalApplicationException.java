package com.todev.ma.shared;

public abstract class InternalApplicationException extends RuntimeException {
    protected InternalApplicationException(String message) {
        super(message);
    }
}
