package com.github.anddd7.dashboard.bilibili.controller;

import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping(value = "/${api.prefix}/${api.version}/history", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class HistoryDataController {

  // 调用analysis的接口, 输出分析数据
  // 读取数据库的已分析数据
}
