package com.github.anddd7.analysis.bilibili.service;

import com.github.anddd7.analysis.bilibili.client.SearchApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryAnalysisService {

  private SearchApiClient searchApiClient;

  @Autowired
  public CategoryAnalysisService(SearchApiClient searchApiClient) {
    this.searchApiClient = searchApiClient;
  }

  /**
   * 一段时间内发布视频的增长排序
   * TODO
   * 从DB获取历史数据
   * 分析计数
   * 输出Controller
   */

}
