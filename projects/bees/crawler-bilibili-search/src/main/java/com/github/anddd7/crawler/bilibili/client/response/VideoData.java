package com.github.anddd7.crawler.bilibili.client.response;

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
}
