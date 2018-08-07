package com.github.anddd7.crawler.bilibili.controller;

import com.github.anddd7.crawler.bilibili.service.SearchCrawlerService;
import com.github.anddd7.crawler.bilibili.service.SearchService;
import com.github.anddd7.model.bilibili.domain.PublishedData;
import io.swagger.annotations.Api;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Api
@RestController
@RequestMapping(value = "/${api.prefix}/${api.version}/search", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SearchController {

  private final SearchService searchService;
  private final SearchCrawlerService searchCrawlerService;

  @Autowired
  public SearchController(
      SearchService searchService,
      SearchCrawlerService searchCrawlerService) {
    this.searchService = searchService;
    this.searchCrawlerService = searchCrawlerService;
  }


  @GetMapping("/published")
  public ResponseEntity<List<PublishedData>> getCurrentPublishedData() {
    return ResponseEntity.ok(searchService.getCurrentPublishedData());
  }

  @GetMapping("/published/history")
  public ResponseEntity<List<List<PublishedData>>> getHistoryPublishedData(
      @RequestParam(value = "from") LocalDateTime from
  ) {
    throw new NotImplementedException();
  }

  /**
   * --------------
   * Background method, only for manually test
   * --------------
   */

  @GetMapping("/crawler/current")
  public ResponseEntity storeCurrentPublishedData() {
    searchCrawlerService.storeCurrentPublishedData();
    return ResponseEntity.ok().build();
  }

  @GetMapping("/crawler/today/videos")
  public ResponseEntity storeCurrentPublishedVideoData() {
    throw new NotImplementedException();
  }
}
