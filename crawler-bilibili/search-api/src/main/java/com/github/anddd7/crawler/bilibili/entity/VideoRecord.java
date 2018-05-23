package com.github.anddd7.crawler.bilibili.entity;

import com.github.anddd7.boot.utils.constant.Constants;
import com.github.anddd7.crawler.bilibili.client.response.VideoData;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
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

  public static VideoRecord buildFrom(VideoData source) {
    return VideoRecord.builder()
        .createTime(Long.valueOf(source.getSenddate()))
        .publishDate(LocalDateTime.parse(source.getPubdate(), Constants.DATE_TIME))
        .tags(Arrays.asList(source.getTag().trim().split(",")))
        .playCount(Integer.valueOf(source.getPlay()))
        .favoriteCount(Integer.valueOf(source.getFavorites()))
        .picUrl(source.getPic())
        .id(source.getId())
        .author(source.getAuthor())
        .duration(Integer.valueOf(source.getDuration()))
        .title(source.getTitle())
        .bulletCount(Integer.valueOf(source.getVideo_review()))
        .url(source.getArcurl())
        .description(source.getDescription())
        .commentCount(Integer.valueOf(source.getReview()))
        .build();
  }
}
