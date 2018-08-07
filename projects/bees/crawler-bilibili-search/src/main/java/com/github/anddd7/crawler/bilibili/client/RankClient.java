package com.github.anddd7.crawler.bilibili.client;

import com.github.anddd7.crawler.bilibili.client.response.RankDataWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bilibili-rank", url = "https://api.bilibili.com")
public interface RankClient {

  /**
   * Bilibili排行榜
   */
  @GetMapping("/x/web-interface/ranking/region")
  RankDataWrapper get(
      @RequestParam("rid") int categoryId,
      @RequestParam("day") int day,
      @RequestParam("original") int original);
}
