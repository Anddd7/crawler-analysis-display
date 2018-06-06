package com.github.anddd7.boot.configuration;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import com.github.anddd7.boot.interceptor.CorrelationInterceptor;
import com.github.anddd7.boot.logging.RequestLoggingFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnWebApplication
public class WebMvcConfiguration implements WebMvcConfigurer {

  private static final String[] ALLOWED_METHODS;

  static {
    ALLOWED_METHODS = new String[]{GET.name(), PUT.name(), POST.name(), OPTIONS.name()};
  }

  private CorrelationInterceptor correlationInterceptor() {
    return new CorrelationInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(correlationInterceptor());
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedMethods(ALLOWED_METHODS);
  }

  @Bean
  public RequestLoggingFilter requestLoggingFilter() {
    return new RequestLoggingFilter();
  }
}
