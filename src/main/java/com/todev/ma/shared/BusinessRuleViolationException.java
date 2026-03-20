package com.todev.ma.shared;

public class BusinessRuleViolationException extends RuntimeException {
  public BusinessRuleViolationException(String message) {
    super(message);
  }
}
