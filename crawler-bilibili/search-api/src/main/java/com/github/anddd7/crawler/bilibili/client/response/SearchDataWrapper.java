package com.github.anddd7.crawler.bilibili.client.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Data;

@Data
public class SearchDataWrapper {

  private List<VideoData> result;
  private String pagesize;
  private String seid;
  private String page;
  /**
   * 请求类型, 本接口固定是search
   */
  @JsonIgnore
  private String rqt_type;
  private String numResults;
  private String code;
  /**
   * 性能指标, 暂不需要
   */
  @JsonIgnore
  private CostTime cost_time;
  private String numPages;
  private String msg;
  private String egg_hit;
}
