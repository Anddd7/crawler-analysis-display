package com.github.anddd7.boot.exception.advice;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(NON_EMPTY)
class ApiError {

  private String status;
  private String code;
  private String title;
  private String detail;
}
