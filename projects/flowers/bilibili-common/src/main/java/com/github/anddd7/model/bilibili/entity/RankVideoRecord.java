package com.github.anddd7.model.bilibili.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RankVideoRecord {

  private int categoryId;
  private String categoryName;

  private String title;
  private String description;
  private String author;
  private String pic;

  private int pts;
  private int coins;
  private int favoriteCount;
  private int playCount;
  private int commentCount;
  private int bulletCount;
}
