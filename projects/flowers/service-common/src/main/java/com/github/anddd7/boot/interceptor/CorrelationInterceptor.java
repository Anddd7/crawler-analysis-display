package com.github.anddd7.boot.interceptor;

import static com.github.anddd7.boot.scope.CorrelationContext.HEADER_CORRELATION_ID;
import static com.github.anddd7.boot.scope.CorrelationContext.HEADER_SESSION_ID;

import com.github.anddd7.boot.scope.CorrelationContextHolder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 记录请求的关联关系, 便于日志查询
 * 每一个服务调用链具有统一的一个CorrelationId, 依次传递
 * 如果用户开启了cookie, 会记录下SessionId作为用户操作标识
 */
@Slf4j
public class CorrelationInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {
    HttpHeaders headers = mappingHeaders(request);

    String correlationId = headers.getFirst(HEADER_CORRELATION_ID);
    if (Strings.isBlank(correlationId)) {
      correlationId = UUID.randomUUID().toString();
      headers.put(HEADER_CORRELATION_ID, Collections.singletonList(correlationId));
    }
    MDC.put(HEADER_CORRELATION_ID, correlationId);

    String sessionId = headers.getFirst(HEADER_SESSION_ID);
    if (Strings.isNotBlank(sessionId)) {
      MDC.put(HEADER_SESSION_ID, sessionId);
    }

    CorrelationContextHolder.get()
        .setCorrelationId(correlationId)
        .setSessionId(sessionId);

    return true;
  }

  private HttpHeaders mappingHeaders(HttpServletRequest request) {
    HttpHeaders headers = new HttpHeaders();

    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      Enumeration<String> header = request.getHeaders(headerName);
      while (header.hasMoreElements()) {
        String value = header.nextElement();
        headers.add(headerName, value);
      }
    }

    return headers;
  }
}
