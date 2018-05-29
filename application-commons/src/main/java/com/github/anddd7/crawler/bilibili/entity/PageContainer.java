package com.github.anddd7.crawler.bilibili.entity;

import java.util.List;
import lombok.Builder;
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
}
