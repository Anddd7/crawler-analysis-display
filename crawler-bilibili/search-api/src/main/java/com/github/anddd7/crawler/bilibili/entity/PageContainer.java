package com.github.anddd7.crawler.bilibili.entity;

import com.github.anddd7.crawler.bilibili.client.response.SearchDataWrapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PageContainer<T> {

  private int pageNumber;
  private int pageSize;
  private int currentElements;
  private int totalPages;
  private int totalElements;
  private boolean hasNext;
  private List<T> elements;

  public static PageContainer<VideoRecord> buildFrom(SearchDataWrapper source) {
    PageContainer pageContainer = PageContainer.<VideoRecord>builder()
        .pageNumber(Integer.valueOf(source.getPage()))
        .pageSize(Integer.valueOf(source.getPagesize()))
        .currentElements(source.getResult().size())
        .totalPages(Integer.valueOf(source.getNumPages()))
        .totalElements(Integer.valueOf(source.getNumResults()))
        .hasNext(source.getPage().equals(source.getNumPages()))
        .elements(
            source.getResult().stream().map(VideoRecord::buildFrom).collect(Collectors.toList()))
        .build();
    return pageContainer;
  }
}
