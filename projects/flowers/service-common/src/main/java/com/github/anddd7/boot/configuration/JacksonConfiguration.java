package com.github.anddd7.boot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.anddd7.util.JacksonJsonTool;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfiguration {

  @Primary
  @Bean
  @ConditionalOnMissingBean
  public ObjectMapper objectMapper() {
    return JacksonJsonTool.getDefaultMapper();
  }
}
