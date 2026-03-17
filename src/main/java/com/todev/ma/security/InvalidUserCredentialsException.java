package com.todev.ma.shared;

public class InvalidUserCredentialsException extends RuntimeException {
  public InvalidUserCredentialsException(String message) {
    super(message);
  }
}
