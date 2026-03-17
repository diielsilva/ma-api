package com.todev.ma.shared;

public abstract class ModelNotFoundException extends RuntimeException {
    protected ModelNotFoundException(String message) {
        super(message);
    }
}
