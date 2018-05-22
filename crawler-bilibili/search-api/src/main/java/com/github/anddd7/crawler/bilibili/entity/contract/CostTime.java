package com.github.anddd7.crawler.bilibili.entity.contract;

import lombok.Data;

@Data
public class CostTime {

  private String total;

  private String as_request;

  private String as_request_format;

  private String as_response_format;

  private String params_check;

  private String main_handler;

  private String illegal_handler;
}
