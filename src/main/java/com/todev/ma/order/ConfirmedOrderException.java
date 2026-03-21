package com.todev.ma.order;

public class ConfirmedOrderException extends RuntimeException {
  public ConfirmedOrderException(String message) {
    super(message);
  }
}
