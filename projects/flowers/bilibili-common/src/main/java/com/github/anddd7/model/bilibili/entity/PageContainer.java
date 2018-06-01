package com.github.anddd7.model.bilibili.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageContainer<T> {

  private int pageNumber;
  private int pageSize;
  private int currentElements;
  private int totalPages;
  private int totalElements;
  private boolean hasNext;
  private List<T> elements;
}
