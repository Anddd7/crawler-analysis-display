package com.github.anddd7.boot.logging;

import static com.github.anddd7.boot.logging.LoggingFormatter.logRequest;
import static com.github.anddd7.boot.logging.LoggingFormatter.logResponse;
import static com.github.anddd7.boot.logging.LoggingFormatter.wrapRequest;
import static com.github.anddd7.boot.logging.LoggingFormatter.wrapResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.AbstractRequestLoggingFilter;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * 参考Spring的日志实现, 过滤了一些不必要的路径
 *
 * @see CommonsRequestLoggingFilter
 * @see AbstractRequestLoggingFilter
 */
@Slf4j
public class RequestLoggingFilter extends OncePerRequestFilter {

  private static final List<String> IGNORE_PATH = Arrays.asList(
      "/swagger-ui.html",
      "/swagger-resources",
      "/v2/api-docs",
      "/webjars/springfox-swagger-ui"
  );

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    if (isAsyncDispatch(request) || shouldLog(request)) {
      doFilterWrapped(wrapRequest(request), wrapResponse(response), filterChain);
    } else {
      filterChain.doFilter(request, response);
    }
  }

  private boolean shouldLog(HttpServletRequest request) {
    return IGNORE_PATH.stream().noneMatch(request.getRequestURI()::startsWith);
  }

  private void doFilterWrapped(ContentCachingRequestWrapper request,
      ContentCachingResponseWrapper response, FilterChain filterChain)
      throws IOException, ServletException {
    long startTime = System.currentTimeMillis();
    try {
      beforeRequest(request);
      filterChain.doFilter(request, response);
    } finally {
      afterRequest(request, response, System.currentTimeMillis() - startTime);
      response.copyBodyToResponse();
    }
  }

  private void beforeRequest(ContentCachingRequestWrapper request) {
    logRequest(log, request);
  }

  private void afterRequest(ContentCachingRequestWrapper request,
      ContentCachingResponseWrapper response, long costTime) {
    logResponse(log, request, response, costTime);
  }
}
