package com.github.anddd7.analysis.bilibili.configuration;

import feign.Logger.Level;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.gson.GsonDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @deprecated 统一配置到services-common中
 * TODO
 */
@Deprecated
@Configuration
public class FeignCorrelationConfiguration {

  @Autowired
  private ObjectFactory<HttpMessageConverters> messageConverters;

  @Bean
  Encoder feignEncoder() {
    return new SpringEncoder(this.messageConverters);
  }

  @Bean
  Decoder feignDecoder() {
    return new GsonDecoder();
  }

  @Bean
  Level feignLoggerLevel() {
    return Level.FULL;
  }
}
