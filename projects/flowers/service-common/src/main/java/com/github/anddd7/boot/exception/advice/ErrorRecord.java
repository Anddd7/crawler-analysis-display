package com.github.anddd7.boot.exception.advice;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static java.lang.String.format;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Getter
@JsonInclude(NON_EMPTY)
public class ErrorRecord {

  public static final ErrorRecord GENERIC_ERROR;

  static {
    GENERIC_ERROR = new ErrorRecord("ErrorRecord");
  }

  private ErrorCode code;
  private String message;

  ErrorRecord(String message) {
    this.code = ErrorCode.Generic;
    this.message = message;
  }

  ErrorRecord(FieldError fieldError) {
    this.code = ErrorCode.Field;
    this.message = format("%s %s", fieldError.getField(), fieldError.getDefaultMessage());
  }

  ErrorRecord(ObjectError objectError) {
    this.code = ErrorCode.Object;
    this.message = format("%s %s", objectError.getObjectName(), objectError.getDefaultMessage());
  }
}
