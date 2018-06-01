package com.github.anddd7.crawler.bilibili.controller;

import com.github.anddd7.model.bilibili.entity.PageContainer;
import com.github.anddd7.model.bilibili.entity.VideoRecord;
import com.github.anddd7.model.bilibili.entity.command.DateRangeCommand;
import com.github.anddd7.model.bilibili.entity.command.SearchByCategoryCommand;
import com.github.anddd7.crawler.bilibili.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/${api.version}/search", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SearchCategoryController {

  private final SearchService searchService;

  @Autowired
  public SearchCategoryController(final SearchService searchService) {
    this.searchService = searchService;
  }

  @GetMapping("/{categoryId}/today")
  public ResponseEntity<PageContainer<VideoRecord>> getToday(
      @PathVariable("categoryId") String categoryId,
      @RequestParam(name = "pageSize", defaultValue = "25") int pageSize,
      @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber) {
    SearchByCategoryCommand command = SearchByCategoryCommand.builder()
        .categoryId(categoryId)
        .pageSize(pageSize)
        .pageNumber(pageNumber)
        .dateRange(DateRangeCommand.today())
        .build();
    return ResponseEntity.ok(searchService.searchByCategory(command));
  }
}
