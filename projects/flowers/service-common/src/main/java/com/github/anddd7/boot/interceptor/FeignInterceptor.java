package com.github.anddd7.boot.interceptor;

import static com.github.anddd7.boot.scope.CorrelationContext.CORRELATION_ID_HEADER;
import static com.github.anddd7.boot.scope.CorrelationContext.SESSION_ID_HEADER;
import static java.util.Collections.singletonList;

import com.github.anddd7.boot.scope.CorrelationContext;
import com.github.anddd7.boot.scope.CorrelationContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * 为Feign Client调用增加Correlation
 */
@Slf4j
public class FeignInterceptor implements RequestInterceptor {

  @Override
  public void apply(RequestTemplate template) {
    CorrelationContext correlationContext = CorrelationContextHolder.get();
    template.header(CORRELATION_ID_HEADER, singletonList(correlationContext.getCorrelationId()));
    template.header(SESSION_ID_HEADER, singletonList(correlationContext.getSessionId()));
  }
}
