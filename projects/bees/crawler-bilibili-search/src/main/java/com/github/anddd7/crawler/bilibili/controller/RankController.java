package com.github.anddd7.crawler.bilibili.controller;

import com.github.anddd7.crawler.bilibili.client.response.RankData;
import com.github.anddd7.crawler.bilibili.service.RankService;
import com.github.anddd7.model.bilibili.entity.Category;
import io.swagger.annotations.Api;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping(value = "/${api.prefix}/${api.version}/rank", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RankController {

  private final RankService rankService;

  @Autowired
  public RankController(RankService rankService) {
    this.rankService = rankService;
  }

  /**
   * --------------
   * Runtime method
   * --------------
   */

  @GetMapping("/today")
  public ResponseEntity<Map<Category, List<RankData>>> getToday() {
    return ResponseEntity.ok(rankService.getTodayRankBoard());
  }

  /**
   * --------------
   * Background method, only for manually test
   * --------------
   */
}
