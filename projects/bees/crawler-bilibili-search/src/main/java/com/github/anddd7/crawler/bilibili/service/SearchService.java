package com.github.anddd7.crawler.bilibili.service;

import com.github.anddd7.crawler.bilibili.client.SearchClient;
import com.github.anddd7.crawler.bilibili.client.response.SearchDataWrapper;
import com.github.anddd7.model.bilibili.constant.CopyRight;
import com.github.anddd7.model.bilibili.constant.Order;
import com.github.anddd7.model.bilibili.entity.PageContainer;
import com.github.anddd7.model.bilibili.entity.VideoRecord;
import com.github.anddd7.model.bilibili.entity.command.SearchByCategoryCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

  /**
   * 固定参数
   */
  private static final String MAIN_VERSION = "v3";
  private static final String SEARCH_TYPE = "video";
  private static final String VIEW_TYPE = "hot_rank";
  private static final String PIC_SIZE = "160x100";

  private final SearchClient searchClient;

  @Autowired
  public SearchService(final SearchClient searchClient) {
    this.searchClient = searchClient;
  }

  public PageContainer<VideoRecord> searchByCategory(SearchByCategoryCommand command) {
    return searchClient.search(
//        ImmutableMap.<String, Object>builder()
//            .put("main_ver", MAIN_VERSION)
//            .put("search_type", SEARCH_TYPE)
//            .put("view_type", VIEW_TYPE)
//            .put("pic_size", PIC_SIZE)
//            .put("order", Order.click.name())
//            .put("copy_right", CopyRight.ALL.getCode())
//            .put("pagesize", command.getPageSize())
//            .put("page", command.getPageNumber())
//            .put("cate_id", command.getCategoryId())
//            .put("time_from", command.getFromDate())
//            .put("time_to", command.getToDate())
//            .build()
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
    ).mappingToPageContainer();
  }

  private PageContainer<VideoRecord> packageVideoRecord(SearchDataWrapper searchDataWrapper) {
    return searchDataWrapper.mappingToPageContainer();
  }

}