package com.github.anddd7.boot.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class InternalErrorException extends ApplicationException {

  public InternalErrorException(String message) {
    super(INTERNAL_SERVER_ERROR, message);
  }
}
