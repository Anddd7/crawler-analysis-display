package com.github.anddd7.boot.configuration;

import com.github.anddd7.boot.interceptor.RestTemplateInterceptor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Rest Template配置
 * @deprecated 使用feign集成组件
 */
@Deprecated
@Configuration
public class RestTemplateConfiguration {

  @Value("${rest.connectTimeout:5000}")
  private Integer connectTimeout;

  @Value("${rest.readTimeout:5000}")
  private Integer readTimeout;

  @Autowired
  private List<ClientHttpRequestInterceptor> interceptors;

  @Primary
  @Bean
  public RestTemplate restTemplate() {
    interceptors.add(new RestTemplateInterceptor());

    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
    factory.setConnectionRequestTimeout(connectTimeout);
    factory.setConnectTimeout(connectTimeout);
    factory.setReadTimeout(readTimeout);

    RestTemplate restTemplate = new RestTemplate(factory);
    restTemplate.setInterceptors(interceptors);
    return restTemplate;
  }
}
