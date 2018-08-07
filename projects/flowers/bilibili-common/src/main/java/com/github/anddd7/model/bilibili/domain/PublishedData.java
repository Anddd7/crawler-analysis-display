package com.github.anddd7.model.bilibili.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "published_data")
public class PublishedData {

  private String batchId;
  private LocalDateTime recordTime;
  private int categoryId;
  private String categoryName;
  private int publishedVideos;
}
