package com.github.anddd7.boot.exception.advice;

public enum ErrorCode {
  /**
   * 普通异常, 返回异常信息
   */
  Generic,
  /**
   * 对某一个参数的校验/监控异常
   */
  Field, Object
}
