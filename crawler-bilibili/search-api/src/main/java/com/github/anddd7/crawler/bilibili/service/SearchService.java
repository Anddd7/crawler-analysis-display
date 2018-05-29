package com.github.anddd7.crawler.bilibili.service;

import com.github.anddd7.crawler.bilibili.client.SearchClient;
import com.github.anddd7.crawler.bilibili.client.response.SearchDataWrapper;
import com.github.anddd7.crawler.bilibili.entity.PageContainer;
import com.github.anddd7.crawler.bilibili.entity.VideoRecord;
import com.github.anddd7.crawler.bilibili.entity.command.SearchByCategoryCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

  private final SearchClient searchClient;

  @Autowired
  public SearchService(final SearchClient searchClient) {
    this.searchClient = searchClient;
  }


  public PageContainer<VideoRecord> searchByCategory(SearchByCategoryCommand command) {
    return packageVideoRecord(searchClient.searchByCategory(command));
  }

  private PageContainer<VideoRecord> packageVideoRecord(SearchDataWrapper searchDataWrapper) {
    return searchDataWrapper.mappingToPageContainer();
  }
}
