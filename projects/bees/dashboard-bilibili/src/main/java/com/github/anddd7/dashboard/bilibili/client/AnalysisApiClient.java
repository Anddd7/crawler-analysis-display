package com.github.anddd7.dashboard.bilibili.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "bilibili-analysis", path = "/api/${api.version}")
public interface AnalysisApiClient {

}

