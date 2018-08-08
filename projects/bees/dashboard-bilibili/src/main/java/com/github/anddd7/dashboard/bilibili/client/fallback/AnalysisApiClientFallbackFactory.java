package com.github.anddd7.dashboard.bilibili.client.fallback;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class AnalysisApiClientFallbackFactory implements
    FallbackFactory<AnalysisApiClientFallBack> {

  @Override
  public AnalysisApiClientFallBack create(Throwable throwable) {
    return new AnalysisApiClientFallBack(throwable);
  }
}