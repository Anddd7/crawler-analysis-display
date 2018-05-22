package com.github.anddd7.crawler.bilibili.entity.contract;

import lombok.Data;

/**
 * TODO ignore and format field
 */
@Data
public class SearchDataWrapper {

  private VideoData[] result;

  private String pagesize;

  private String seid;

  private String page;

  private String rqt_type;

  private String numResults;

  private String code;

  private CostTime cost_time;

  private String numPages;

  private String msg;

  private String egg_hit;
}
