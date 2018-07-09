package com.github.anddd7.boot.configuration;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import com.github.anddd7.boot.interceptor.CorrelationFilter;
import com.github.anddd7.boot.logging.RequestLoggingFilter;
import javax.servlet.Filter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnWebApplication
public class WebMvcConfiguration implements WebMvcConfigurer {

  private static final String[] ALLOWED_METHODS;

  static {
    ALLOWED_METHODS = new String[]{GET.name(), PUT.name(), POST.name(), OPTIONS.name()};
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedMethods(ALLOWED_METHODS);
  }

  @Bean
  public Filter correlationFilter() {
    return new CorrelationFilter();
  }

  @Bean
  public Filter requestLoggingFilter() {
    return new RequestLoggingFilter();
  }
}
