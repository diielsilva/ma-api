package com.todev.ma.user;

public class DuplicatedEmailException extends RuntimeException {
  public DuplicatedEmailException(String message) {
    super(message);
  }
}
