package com.github.anddd7.boot.interceptor;

import static com.github.anddd7.boot.scope.CorrelationContext.CORRELATION_ID_HEADER;
import static com.github.anddd7.boot.scope.CorrelationContext.SESSION_ID_HEADER;
import static java.util.Collections.singletonList;

import com.github.anddd7.boot.scope.CorrelationContext;
import com.github.anddd7.boot.scope.CorrelationContextHolder;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import java.io.IOException;
import java.io.InputStreamReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * 为RestTemplate调用增加Correlation, 并打印调用日志
 *
 * @deprecated 修改为适配Feign Client的拦截器
 * TODO
 */
@Deprecated
@Slf4j
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body,
      ClientHttpRequestExecution execution) throws IOException {
    CorrelationContext correlationContext = CorrelationContextHolder.get();

    request.getHeaders()
        .putIfAbsent(CORRELATION_ID_HEADER, singletonList(correlationContext.getCorrelationId()));
    request.getHeaders()
        .putIfAbsent(SESSION_ID_HEADER, singletonList(correlationContext.getSessionId()));

    logRequest(request, body);
    ClientHttpResponse response = execution.execute(request, body);
    logResponse(response);
    return response;
  }

  private void logRequest(HttpRequest request, byte[] body) {
    log.info(String.format("Request sent - uri: %s, method: %s, headers: %s, body: %s",
        request.getURI(),
        request.getMethod(),
        filterHeaders(copyHeaders(request)),
        new String(body, Charsets.UTF_8)
    ));
  }

  private void logResponse(ClientHttpResponse response) throws IOException {
    String responseBody = CharStreams.toString(
        new InputStreamReader(response.getBody(), Charsets.UTF_8));
    log.info(
        String.format("Response received - status code: %s, status text: %s, headers: %s, body: %s",
            response.getStatusCode(),
            response.getStatusText(),
            filterHeaders(response.getHeaders()),
            responseBody
        ));
  }

  /**
   * 可以对header进行隐藏处理
   */
  private Object filterHeaders(HttpHeaders headers) {
    return headers;
  }

  private HttpHeaders copyHeaders(HttpRequest request) {
    HttpHeaders headers = new HttpHeaders();
    HttpHeaders requestHeaders = request.getHeaders();
    for (String key : requestHeaders.keySet()) {
      headers.put(key, requestHeaders.get(key));
    }
    return headers;
  }

}
