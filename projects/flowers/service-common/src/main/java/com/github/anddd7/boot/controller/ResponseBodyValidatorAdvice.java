package com.github.anddd7.boot.controller;

import java.util.Objects;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 对response body进行拦截, 可以进行最后的监控/校验/处理
 */
@Slf4j
@RestControllerAdvice
public class ResponseBodyValidatorAdvice implements ResponseBodyAdvice<Object> {

  @Value("${response.validation:false}")
  private boolean shouldValidate;

  @Autowired
  private Validator validator;

  @Override
  public boolean supports(MethodParameter returnType,
      Class<? extends HttpMessageConverter<?>> converterType) {
    return shouldValidate && !Objects.isNull(returnType.getParameterAnnotation(Valid.class));
  }

  @Override
  public Object beforeBodyWrite(Object body,
      MethodParameter returnType, MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request, ServerHttpResponse response) {
    if (!Objects.isNull(body)) {
      Set<ConstraintViolation<Object>> constraintViolations = this.validator.validate(body);
      if (!constraintViolations.isEmpty()) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        log.error("Response validation check failed: {}", constraintViolations);
        return null;
      }
    }
    return body;
  }
}
