package com.github.anddd7.boot.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ResourceNotFoundException extends ApplicationException {

  public ResourceNotFoundException(String message) {
    super(NOT_FOUND, message);
  }
}
