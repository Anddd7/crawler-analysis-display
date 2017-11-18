package github.eddy.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * @author edliao 有关日期和时间的工具库
 */
public class DateTools {

  static final DateTimeFormatter Y4M2D2 = DateTimeFormatter.ofPattern("yyyyMMdd");
  static final DateTimeFormatter Y4M2 = DateTimeFormatter.ofPattern("yyyyMM");

  /**
   * 日期包装类 ,包含一些常用的日期和format方法
   */
  public static class DateBuilder {

    LocalDate localDate;

    public DateBuilder(LocalDate localDate) {
      this.localDate = localDate;
    }

    public static DateBuilder ofYesterday() {
      return new DateBuilder(LocalDate.now().minusDays(1));
    }

    public static DateBuilder ofToday() {
      return new DateBuilder(LocalDate.now());
    }

    public static DateBuilder of(Integer year, Integer month) {
      return of(year, month, 1);
    }

    public static DateBuilder of(Integer year, Integer month, Integer day) {
      return new DateBuilder(LocalDate.of(year, month, day));
    }

    public DateBuilder lastDayOfMonth() {
      return new DateBuilder(localDate.with(TemporalAdjusters.lastDayOfMonth()));
    }

    public String format(String format) {
      return DateTimeFormatter.ofPattern(format).format(localDate);
    }

    public String formatY4M2D2() {
      return Y4M2D2.format(localDate);
    }

    public String formatY4M2() {
      return Y4M2.format(localDate);
    }
  }


  /**
   * TODO 生成cron表达式
   */
  public static String cron() {
    return null;
  }

  public static void main(String[] args) {

  }
}
