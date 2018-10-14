package com.github.anddd7.boot.configuration;

import feign.Logger.Level;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 对Feign相关的bean组件的额外配置, 也可以用来覆盖默认配置
 *
 * @see FeignClientProperties 参数配置
 * @see FeignClientsConfiguration 组件配置
 */
@Configuration
public class FeignConfiguration {

  @Bean
  @ConditionalOnMissingBean
  Level feignLoggerLevel() {
    return Level.FULL;
  }
}
