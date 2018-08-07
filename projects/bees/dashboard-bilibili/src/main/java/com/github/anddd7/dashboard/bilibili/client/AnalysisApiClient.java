package com.github.anddd7.dashboard.bilibili.client;

import com.github.anddd7.model.bilibili.entity.PublishedRecord;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "bilibili-analysis", path = "/api/${api.version}")
public interface AnalysisApiClient {

  @GetMapping("/analysis/published")
  List<PublishedRecord> getTop10OfPublishedData();
}

