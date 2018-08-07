package com.github.anddd7.model.bilibili.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PublishedRecord {

  private int categoryId;
  private String categoryName;
  private int publishedVideos;

  private int newVideosLastHalfHour;
  private int newVideosLastHour;
}
