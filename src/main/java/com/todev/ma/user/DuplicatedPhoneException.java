package com.todev.ma.user;

public class DuplicatedPhoneException extends RuntimeException {
  public DuplicatedPhoneException(String message) {
    super(message);
  }
}
