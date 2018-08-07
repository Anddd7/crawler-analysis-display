package com.github.anddd7.crawler.bilibili.controller;

import com.github.anddd7.crawler.bilibili.service.SearchService;
import com.github.anddd7.model.bilibili.entity.PageContainer;
import com.github.anddd7.model.bilibili.entity.VideoRecord;
import com.github.anddd7.model.bilibili.entity.command.SearchByCategoryCommand;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping(value = "/${api.prefix}/${api.version}/search", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SearchCategoryController {

  private final SearchService searchService;

  @Autowired
  public SearchCategoryController(final SearchService searchService) {
    this.searchService = searchService;
  }

  @GetMapping
  public ResponseEntity<PageContainer<VideoRecord>> searchByCategory(
      SearchByCategoryCommand command) {
    return ResponseEntity.ok(searchService.getTodayVideosByCategory(command));
  }
}
