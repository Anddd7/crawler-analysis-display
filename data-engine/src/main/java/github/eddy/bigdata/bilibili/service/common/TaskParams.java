package github.eddy.bigdata.bilibili.service.common;


import static github.eddy.common.DateTools.DateBuilder.of;

import github.eddy.common.ReflectTool;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author edliao
 * @since 11/16/2017 执行任务所用的参数封装
 *
 * 使用POJO类代替不定长度参数列表 ,路由的时候可以使用反射直接找到对应方法并执行
 */
@Getter
@Setter
@Accessors(chain = true)
@Slf4j
public class TaskParams {

  Integer cateId;
  Integer year;
  Integer month;
  Integer day;

  String dayDate;
  String monthDate;

  Map innerMap = null;


  public String getDayDate() {
    return dayDate == null ?
        setDayDate(of(year, month, day).formatY4M2D2()).getDayDate() : dayDate;
  }

  public String getMonthDate() {
    return monthDate == null ?
        setMonthDate(getDayDate().substring(0, dayDate.length() - 2)).getMonthDate() : monthDate;
  }

  public Map toMap() {
    if (innerMap != null) {
      return innerMap;
    }
    try {
      innerMap = ReflectTool.convertPOJO2Map(this, true);
    } catch (IllegalAccessException e) {
      log.error("", e);
    }
    return innerMap;
  }
}
