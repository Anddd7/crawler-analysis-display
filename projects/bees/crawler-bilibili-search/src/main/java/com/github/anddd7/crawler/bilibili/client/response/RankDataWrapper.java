package com.github.anddd7.crawler.bilibili.client.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RankDataWrapper {

  private int code;
  private List<RankData> data;
  private String message;
  private int ttl;
}
