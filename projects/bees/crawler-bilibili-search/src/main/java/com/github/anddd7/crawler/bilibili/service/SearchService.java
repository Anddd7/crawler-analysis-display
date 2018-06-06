package com.github.anddd7.crawler.bilibili.service;

import com.github.anddd7.crawler.bilibili.client.Search1Client;
import com.github.anddd7.crawler.bilibili.client.response.SearchDataWrapper;
import com.github.anddd7.model.bilibili.entity.PageContainer;
import com.github.anddd7.model.bilibili.entity.VideoRecord;
import com.github.anddd7.model.bilibili.entity.command.SearchByCategoryCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

  private final Search1Client searchClient;

  @Autowired
  public SearchService(final Search1Client searchClient) {
    this.searchClient = searchClient;
  }


  public PageContainer<VideoRecord> searchByCategory(SearchByCategoryCommand command) {
    return packageVideoRecord(searchClient.searchByCategory(command));
  }

  private PageContainer<VideoRecord> packageVideoRecord(SearchDataWrapper searchDataWrapper) {
    return searchDataWrapper.mappingToPageContainer();
  }
}
