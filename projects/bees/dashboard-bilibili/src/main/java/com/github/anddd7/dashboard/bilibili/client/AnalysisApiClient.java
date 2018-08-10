package com.github.anddd7.dashboard.bilibili.client;

import com.github.anddd7.dashboard.bilibili.client.fallback.AnalysisApiClientFallbackFactory;
import com.github.anddd7.model.bilibili.entity.PublishedRecord;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "analysis-bilibili", path = "/api/${api.version}", fallbackFactory = AnalysisApiClientFallbackFactory.class)
public interface AnalysisApiClient {

  @GetMapping("/analysis/published")
  List<PublishedRecord> getCalculatedPublishedRecord();
}

