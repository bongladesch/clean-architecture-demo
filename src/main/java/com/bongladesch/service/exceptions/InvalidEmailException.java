package com.bongladesch.service.exceptions;

public class InvalidEmailException extends RuntimeException {

  public InvalidEmailException(String message) {
    super(message);
  }
}
