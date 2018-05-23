package com.github.anddd7.boot.utils.constant;

import java.time.format.DateTimeFormatter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

  public static final String CONTENT_TYPE = "application/json";

  public static final DateTimeFormatter DATE_NO_SEPARATOR = DateTimeFormatter
      .ofPattern("yyyyMMdd");

  public static final DateTimeFormatter DATE_TIME = DateTimeFormatter
      .ofPattern("yyyy-MM-dd HH:mm:ss");
}
