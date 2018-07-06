package com.github.anddd7.boot.interceptor;

import static com.github.anddd7.boot.scope.CorrelationContext.HEADER_CORRELATION_ID;
import static com.github.anddd7.boot.scope.CorrelationContext.HEADER_SESSION_ID;
import static java.util.Collections.singletonList;

import com.github.anddd7.boot.scope.CorrelationContext;
import com.github.anddd7.boot.scope.CorrelationContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * 在Feign Client调用时注入Correlation
 */
@Slf4j
public class FeignInterceptor implements RequestInterceptor {

  @Override
  public void apply(RequestTemplate template) {
    CorrelationContext correlationContext = CorrelationContextHolder.get();
    template.header(HEADER_CORRELATION_ID, singletonList(correlationContext.getCorrelationId()));
    template.header(HEADER_SESSION_ID, singletonList(correlationContext.getSessionId()));
  }
}
