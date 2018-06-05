package com.github.anddd7.boot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Rest Template配置
 */
@Configuration
public class RestTemplateConfiguration {

  @Value("${rest.connectTimeout:5000}")
  private Integer connectTimeout;

  @Value("${rest.readTimeout:5000}")
  private Integer readTimeout;

  @Primary
  @Bean
  @ConditionalOnMissingBean
  public RestTemplate restTemplate() {
    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    requestFactory.setConnectTimeout(connectTimeout);
    requestFactory.setReadTimeout(readTimeout);
    return new RestTemplate(requestFactory);
  }
}
