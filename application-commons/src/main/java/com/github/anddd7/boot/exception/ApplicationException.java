package com.github.anddd7.boot.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public abstract class ApplicationException extends RuntimeException {

  private HttpStatus status;
  private String message;
}
