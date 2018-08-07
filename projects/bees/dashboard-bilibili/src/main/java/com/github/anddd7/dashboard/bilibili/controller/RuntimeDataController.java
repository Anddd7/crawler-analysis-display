package com.github.anddd7.dashboard.bilibili.controller;

import com.github.anddd7.dashboard.bilibili.client.SearchApiClient;
import com.github.anddd7.model.bilibili.entity.Category;
import com.github.anddd7.model.bilibili.entity.PageContainer;
import com.github.anddd7.model.bilibili.entity.RankVideoRecord;
import com.github.anddd7.model.bilibili.entity.VideoRecord;
import com.github.anddd7.model.bilibili.entity.command.DateRangeCommand;
import com.github.anddd7.model.bilibili.entity.command.SearchByCategoryCommand;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping(value = "/${api.prefix}/${api.version}/runtime", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RuntimeDataController {

  private SearchApiClient searchApiClient;

  @Autowired
  public RuntimeDataController(SearchApiClient searchApiClient) {
    this.searchApiClient = searchApiClient;
  }

  @GetMapping("/category")
  public ResponseEntity<List<Category>> getCategories() {
    return ResponseEntity.ok(searchApiClient.getCategories());
  }

  @GetMapping("/{categoryId}/today/page")
  public ResponseEntity<PageContainer<VideoRecord>> getTodayVideosWithPage(
      @PathVariable("categoryId") int categoryId,
      @RequestParam(name = "pageSize", defaultValue = "25") int pageSize,
      @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber) {
    SearchByCategoryCommand command = SearchByCategoryCommand.builder()
        .categoryId(categoryId)
        .pageSize(pageSize)
        .pageNumber(pageNumber)
        .dateRange(DateRangeCommand.today())
        .build();
    return ResponseEntity.ok(searchApiClient.searchByCategory(command));
  }


  @GetMapping("/rank/today")
  public ResponseEntity<List<RankVideoRecord>> getTodayRankBoard() {
    return ResponseEntity.ok(searchApiClient.getRankBoard(1));
  }
}
