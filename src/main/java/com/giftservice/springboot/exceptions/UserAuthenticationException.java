package com.giftservice.springboot.exceptions;

public class UserAuthenticationException extends RuntimeException {
  public UserAuthenticationException(String message) {
    super(message);
  }
}
