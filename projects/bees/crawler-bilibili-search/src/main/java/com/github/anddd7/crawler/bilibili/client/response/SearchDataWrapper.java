package com.github.anddd7.crawler.bilibili.client.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchDataWrapper {

  private List<VideoData> result;
  private int pagesize;
  private String seid;
  private int page;

  /**
   * 请求类型, 本接口固定是search
   */
  @JsonIgnore
  private String rqt_type;

  private int numResults;
  private String code;

  /**
   * 性能指标, 暂不需要
   */
  @JsonIgnore
  private CostTime cost_time;

  private int numPages;
  private String msg;
  private String egg_hit;
}
