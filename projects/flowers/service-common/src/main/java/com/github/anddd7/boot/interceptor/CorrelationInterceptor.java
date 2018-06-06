package com.github.anddd7.boot.interceptor;

import static com.github.anddd7.boot.scope.CorrelationContext.CORRELATION_ID_HEADER;
import static com.github.anddd7.boot.scope.CorrelationContext.SESSION_ID_HEADER;

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

    String correlationId = headers.getFirst(CORRELATION_ID_HEADER);
    if (Strings.isBlank(correlationId)) {
      correlationId = UUID.randomUUID().toString();
      headers.put(CORRELATION_ID_HEADER, Collections.singletonList(correlationId));
    }
    MDC.put(CORRELATION_ID_HEADER, correlationId);

    String sessionId = headers.getFirst(SESSION_ID_HEADER);
    if (Strings.isNotBlank(sessionId)) {
      MDC.put(SESSION_ID_HEADER, sessionId);
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
