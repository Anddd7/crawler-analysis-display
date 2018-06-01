package com.github.anddd7.analysis.bilibili.client;


import com.github.anddd7.model.bilibili.entity.Category;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 对 search api 的调用
 */
@FeignClient(value = "bilibili-search-api", path = "/api/${api.version}")
public interface SearchApiClient {

  @GetMapping(value = "/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<List<Category>> getCategories();
}

