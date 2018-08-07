package com.github.anddd7.crawler.bilibili.client.response;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "rank-data")
public class RankData {

  private String aid;
  private String author;
  private boolean badgepay;
  private int coins;
  private String create;
  private String description;
  private String duration;
  // 收藏
  private int favorites;
  private int mid;
  private String pic;
  // 播放
  private int play;
  // 综合评分
  private int pts;
  // 评论
  private int review;
  private String subtitle;
  private String title;
  private String typename;
  // 弹幕
  private int video_review;
}
