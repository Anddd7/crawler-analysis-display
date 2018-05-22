package com.github.anddd7.boot.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.Builder;

/**
 * 方法还未实现
 */
@Builder
public class NotSupportedException extends ApplicationException {

  public NotSupportedException() {
    super(NOT_FOUND, "Method is not implemented.");
  }
}
