package com.todev.ma.shared;

public abstract class SecurityException extends RuntimeException {
    protected SecurityException(String message) {
        super(message);
    }
}
