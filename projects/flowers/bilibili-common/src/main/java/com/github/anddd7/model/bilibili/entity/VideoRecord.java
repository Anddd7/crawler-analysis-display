package com.github.anddd7.model.bilibili.entity;

import java.time.LocalDateTime;
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
public class VideoRecord {

  private Long createTime;
  private LocalDateTime publishDate;
  private List<String> tags;
  private int playCount;
  private int favoriteCount;
  private String picUrl;
  private String id;
  private String author;
  private int duration;
  private String title;
  private int bulletCount;
  private String url;
  private String description;
  private int commentCount;
}
