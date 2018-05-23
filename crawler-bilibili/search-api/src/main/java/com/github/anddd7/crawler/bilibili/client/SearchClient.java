package com.github.anddd7.crawler.bilibili.client;

import com.github.anddd7.crawler.bilibili.client.response.SearchDataWrapper;
import com.github.anddd7.crawler.bilibili.controller.command.SearchByCategoryCommand;
import com.github.anddd7.crawler.bilibili.utils.constant.CopyRight;
import com.github.anddd7.crawler.bilibili.utils.constant.Order;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 对 bilibili search api 的调用
 */
@Service
public class SearchClient {

  /**
   * 固定参数
   */
  private static final String BASE_URL = "https://s.search.bilibili.com/cate/search";
  private static final String MAIN_VERSION = "v3";
  private static final String SEARCH_TYPE = "video";
  private static final String VIEW_TYPE = "hot_rank";
  private static final String PIC_SIZE = "160x100";

  private final RestTemplate restTemplate;

  @Autowired
  public SearchClient(final RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public SearchDataWrapper searchByCategory(SearchByCategoryCommand command) {
    String queryParameters = Joiner.on("&")
        .withKeyValueSeparator("=")
        .join(ImmutableMap.builder()
            .put("main_ver", MAIN_VERSION)
            .put("search_type", SEARCH_TYPE)
            .put("view_type", VIEW_TYPE)
            .put("pic_size", PIC_SIZE)
            .put("order", Order.click.name())
            .put("copy_right", CopyRight.ALL.getCode())
            .put("pagesize", command.getPageSize())
            .put("page", command.getPageNumber())
            .put("cate_id", command.getCategoryId())
            .put("time_from", command.getFromDate())
            .put("time_to", command.getToDate())
            .build());
    URI uri = URI.create(String.format("%s?%s", BASE_URL, queryParameters));
    return restTemplate.getForObject(uri, SearchDataWrapper.class);
  }
}
