package com.github.anddd7.boot.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.github.anddd7.boot.exception.ApplicationException;
import com.github.anddd7.boot.exception.advice.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 对所有异常进行拦截处理
 *
 * @see ResponseEntityExceptionHandler 包含了Spring自定义异常->Java异常的映射
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

  @Value("${response.errors.hide-detail:true}")
  private boolean hideErrorDetails;

  @ExceptionHandler(Exception.class)
  public ResponseEntity handleGenericException(RuntimeException ex, WebRequest request) {
    return handleErrorResponse(
        ex,
        new ErrorResponse(ex.getMessage()),
        HttpStatus.INTERNAL_SERVER_ERROR,
        request);
  }

  @ExceptionHandler(ApplicationException.class)
  public ResponseEntity handleApplicationException(ApplicationException ex,
      WebRequest request) {
    return handleErrorResponse(
        ex,
        new ErrorResponse(ex.getMessage()),
        ex.getStatus(),
        request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    return handleErrorResponse(
        ex,
        new ErrorResponse(ex.getBindingResult()),
        BAD_REQUEST,
        request);
  }

  private ResponseEntity<Object> handleErrorResponse(Exception ex, ErrorResponse body,
      HttpStatus status,
      WebRequest request) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return handleExceptionInternal(
        ex,
        hideErrorDetails ? ErrorResponse.GENERIC_RESPONSE : body,
        headers,
        status,
        request
    );
  }
}
