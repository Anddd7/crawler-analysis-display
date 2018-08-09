package com.github.anddd7.boot.interceptor;

import static com.github.anddd7.boot.scope.CorrelationContext.HEADER_CORRELATION_ID;
import static com.github.anddd7.boot.scope.CorrelationContext.HEADER_SESSION_ID;
import static com.github.anddd7.util.builder.OptionalString.of;

import com.github.anddd7.boot.configuration.SwaggerConfiguration;
import com.github.anddd7.boot.scope.CorrelationContextHolder;
import com.github.anddd7.util.builder.OptionalString;
import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 记录请求的关联关系, 便于日志查询
 * 每一个服务调用链具有统一的一个CorrelationId, 依次传递
 * 如果用户开启了cookie, 会记录下SessionId作为用户操作标识
 */
@Slf4j
public class CorrelationFilter extends OncePerRequestFilter {

  private static HttpHeaders mappingHeaders(HttpServletRequest request) {
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

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    if (SwaggerConfiguration.ignoreURI(request.getRequestURI())) {
      filterChain.doFilter(request, response);
    } else {
      doFilterWrapped(request, response, filterChain);
    }
  }

  private void doFilterWrapped(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain)
      throws IOException, ServletException {

    HttpHeaders requestHeaders = mappingHeaders(request);
    OptionalString correlationId = of(requestHeaders.getFirst(HEADER_CORRELATION_ID))
        .or(UUID.randomUUID()::toString);
    OptionalString sessionId = of(requestHeaders.getFirst(HEADER_SESSION_ID));

    correlationId.ifNotBlank(id -> MDC.put(HEADER_CORRELATION_ID, id));
    sessionId.ifNotBlank(id -> MDC.put(HEADER_SESSION_ID, id));

    CorrelationContextHolder.get()
        .setCorrelationId(correlationId.toString())
        .setSessionId(sessionId.toString());
    log.debug("Filter got a correlation id - {}", correlationId, sessionId);

    try {
      filterChain.doFilter(request, response);
    } finally {
      response.addHeader(HEADER_CORRELATION_ID, correlationId.toString());
    }
  }
}
