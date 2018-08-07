package com.github.anddd7.dashboard.bilibili.client;

import com.github.anddd7.model.bilibili.entity.Category;
import com.github.anddd7.model.bilibili.entity.PageContainer;
import com.github.anddd7.model.bilibili.entity.RankVideoRecord;
import com.github.anddd7.model.bilibili.entity.VideoRecord;
import com.github.anddd7.model.bilibili.entity.command.SearchByCategoryCommand;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "crawler-bilibili-search", path = "/api/${api.version}")
public interface SearchApiClient {

  @GetMapping("/category")
  List<Category> getCategories();

  @GetMapping("/search")
  PageContainer<VideoRecord> searchByCategory(SearchByCategoryCommand command);

  @GetMapping("/rank")
  List<RankVideoRecord> getRankBoard(int day);
}

