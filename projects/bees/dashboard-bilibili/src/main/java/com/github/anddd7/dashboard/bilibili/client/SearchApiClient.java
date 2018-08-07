package com.github.anddd7.dashboard.bilibili.client;

import com.github.anddd7.model.bilibili.domain.PublishedData;
import com.github.anddd7.model.bilibili.entity.PageContainer;
import com.github.anddd7.model.bilibili.entity.VideoRecord;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "bilibili-search", path = "/api/${api.version}")
public interface SearchApiClient {

  /**
   * SearchCategoryController
   */
  @GetMapping("/{categoryId}/today")
  ResponseEntity<PageContainer<VideoRecord>> getToday(
      @PathVariable("categoryId") int categoryId,
      @RequestParam("pageSize") int pageSize,
      @RequestParam("pageNumber") int pageNumber);

  /**
   * SearchController
   * TODO format PublishedData bean
   */
  @GetMapping("/published")
  ResponseEntity<List<PublishedData>> getCurrentPublishedData();

  /**
   * RankController
   * TODO format RankData bean
   */
//  @GetMapping("/rank/today")
//  ResponseEntity<Map<Category, List<RankData>>> getToday();
}

