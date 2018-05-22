package com.github.anddd7.boot.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public abstract class ApplicationException extends RuntimeException {

  private final HttpStatus status;
  private final String message;

  public ResponseEntity<String> toResponse() {
    return ResponseEntity.status(status).body(message);
  }
}
