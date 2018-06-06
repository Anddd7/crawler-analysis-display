package com.github.anddd7.crawler.bilibili.client;

import com.github.anddd7.boot.properties.RemoteServerProperties;
import com.github.anddd7.crawler.bilibili.client.response.SearchDataWrapper;
import com.github.anddd7.model.bilibili.constant.CopyRight;
import com.github.anddd7.model.bilibili.constant.Order;
import com.github.anddd7.model.bilibili.entity.command.SearchByCategoryCommand;
import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 对 bilibili search api 的调用
 */
@Service
public class Search1Client {

  /**
   * 固定参数
   */
  private static final String MAIN_VERSION = "v3";
  private static final String SEARCH_TYPE = "video";
  private static final String VIEW_TYPE = "hot_rank";
  private static final String PIC_SIZE = "160x100";

  private final SearchClient searchClient;
  private final RemoteServerProperties remoteServerProperties;

  @Autowired
  public Search1Client(final RemoteServerProperties remoteServerProperties) {
    this.remoteServerProperties = remoteServerProperties;
    this.searchClient = Feign.builder()
//        .decoder()
        .target(SearchClient.class, remoteServerProperties.getURI());
  }

  public SearchDataWrapper searchByCategory(SearchByCategoryCommand command) {
    return searchClient.search(
        MAIN_VERSION,
        SEARCH_TYPE,
        VIEW_TYPE,
        PIC_SIZE,
        Order.click.name(),
        CopyRight.ALL.getCode(),
        command.getPageSize(),
        command.getPageNumber(),
        command.getCategoryId(),
        command.getFromDate(),
        command.getToDate()
    );
  }
}
