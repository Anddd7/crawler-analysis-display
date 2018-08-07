package com.github.anddd7.crawler.bilibili.controller;

import com.github.anddd7.crawler.bilibili.service.RankService;
import com.github.anddd7.model.bilibili.entity.RankVideoRecord;
import io.swagger.annotations.Api;
import java.util.List;
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

  @GetMapping
  public ResponseEntity<List<List<RankVideoRecord>>> getRankBoard(int day) {
    return ResponseEntity.ok(rankService.getRankBoard(day));
  }

  /**
   * --------------
   * Background method, only for manually test
   * --------------
   */
}
