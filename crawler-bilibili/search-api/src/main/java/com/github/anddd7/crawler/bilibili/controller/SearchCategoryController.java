package com.github.anddd7.crawler.bilibili.controller;

import com.github.anddd7.boot.utils.constant.Constants;
import com.github.anddd7.crawler.bilibili.client.SearchClient;
import com.github.anddd7.crawler.bilibili.controller.command.DateRangeCommand;
import com.github.anddd7.crawler.bilibili.controller.command.SearchByCategoryCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/${api.version}/search", produces = Constants.CONTENT_TYPE)
public class SearchCategoryController {

  private final SearchClient searchClient;

  @Autowired
  public SearchCategoryController(final SearchClient searchClient) {
    this.searchClient = searchClient;
  }

  @GetMapping("/{categoryId}/today")
  public ResponseEntity getToday(
      @PathVariable("categoryId") String categoryId,
//      @RequestParam("queryToken") String queryToken,
      @RequestParam(name = "pageSize", defaultValue = "25") int pageSize,
      @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber) {
    return searchClient.searchByCategory(
        SearchByCategoryCommand.builder()
            .categoryId(categoryId)
            .pageSize(pageSize)
            .pageNumber(pageNumber)
            .dateRange(DateRangeCommand.today())
            .build());
  }

}
