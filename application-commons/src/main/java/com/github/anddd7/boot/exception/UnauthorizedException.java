package com.github.anddd7.boot.exception;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class UnauthorizedException extends ApplicationException {

  public UnauthorizedException(String message) {
    super(UNAUTHORIZED, message);
  }
}
