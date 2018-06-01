package com.github.anddd7.model.bilibili.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 视频搜索条件
 */
@AllArgsConstructor
@Getter
public enum CopyRight {
  /**
   * 全部
   */
  ALL(-1),
  /**
   * 原创
   */
  OWN(1);

  private int code;
}