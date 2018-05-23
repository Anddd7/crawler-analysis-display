package com.github.anddd7.crawler.bilibili.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Category {

  private Integer categoryId;
  private String description;
}
