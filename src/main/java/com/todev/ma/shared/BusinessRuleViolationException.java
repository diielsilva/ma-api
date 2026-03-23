package com.todev.ma.shared;

public abstract class BusinessRuleViolationException extends RuntimeException {
    protected BusinessRuleViolationException(String message) {
        super(message);
    }
}
