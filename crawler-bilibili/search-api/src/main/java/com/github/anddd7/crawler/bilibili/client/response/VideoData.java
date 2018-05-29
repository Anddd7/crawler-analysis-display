package com.github.anddd7.crawler.bilibili.client.response;

import com.github.anddd7.boot.utils.constant.Constants;
import com.github.anddd7.crawler.bilibili.entity.VideoRecord;
import java.time.LocalDateTime;
import java.util.Arrays;
import lombok.Data;

@Data
public class VideoData {

  private String senddate;
  private String pubdate;
  private String tag;
  private String play;
  private String favorites;
  private String pic;
  /**
   * 数据类型, 本接口固定是video
   */
  private String type;
  private String id;
  private String author;
  private String duration;
  private String title;
  private String video_review;
  private String badgepay;
  private String arcurl;
  private String description;
  private String arcrank;
  private String mid;
  private String review;

  public VideoRecord mappingToVideoRecord() {
    return VideoRecord.builder()
        .createTime(Long.valueOf(this.getSenddate()))
        .publishDate(LocalDateTime.parse(this.getPubdate(), Constants.DATE_TIME))
        .tags(Arrays.asList(this.getTag().trim().split(",")))
        .playCount(Integer.valueOf(this.getPlay()))
        .favoriteCount(Integer.valueOf(this.getFavorites()))
        .picUrl(this.getPic())
        .id(this.getId())
        .author(this.getAuthor())
        .duration(Integer.valueOf(this.getDuration()))
        .title(this.getTitle())
        .bulletCount(Integer.valueOf(this.getVideo_review()))
        .url(this.getArcurl())
        .description(this.getDescription())
        .commentCount(Integer.valueOf(this.getReview()))
        .build();
  }
}
