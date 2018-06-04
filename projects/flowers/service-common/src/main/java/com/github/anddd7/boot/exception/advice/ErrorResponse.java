package com.github.anddd7.boot.exception.advice;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
@JsonInclude(NON_EMPTY)
public class ErrorResponse {

  public static final ErrorResponse GENERIC_RESPONSE;

  static {
    GENERIC_RESPONSE = new ErrorResponse(Collections.singletonList(ErrorRecord.GENERIC_ERROR));
  }

  private List<ErrorRecord> errors;

  private ErrorResponse(List<ErrorRecord> errors) {
    this.errors = errors;
  }

  public ErrorResponse(String message) {
    this.errors = Collections.singletonList(new ErrorRecord(message));
  }

  public ErrorResponse(Errors validationErrors) {
    this.errors = new ArrayList<>();
    validationErrors.getFieldErrors().stream().map(ErrorRecord::new).forEach(errors::add);
    validationErrors.getGlobalErrors().stream().map(ErrorRecord::new).forEach(errors::add);
  }
}
