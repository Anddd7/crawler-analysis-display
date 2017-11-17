package github.eddy.common;

import static java.time.LocalDate.now;
import static java.time.LocalDate.of;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateTools {

  static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
  static final DateTimeFormatter yyyyMM = DateTimeFormatter.ofPattern("yyyyMM");

  public static String[] getDateRangeYYYYMMDD(Integer year, Integer month) {
    LocalDate startDay = of(year, month, 1);
    LocalDate endDay = startDay.with(TemporalAdjusters.lastDayOfMonth());
    return new String[]{yyyyMMdd.format(startDay), yyyyMMdd.format(endDay)};
  }

  public static String getYYYYMMDD(Integer year, Integer month, Integer day) {
    return yyyyMMdd.format(of(year, month, day));
  }

  public static String getYYYYMM(Integer year, Integer month) {
    return yyyyMM.format(of(year, month, 1));
  }

  public static String getYYYYDD() {
    return yyyyMM.format(now());
  }


  public static String cron(){
    return null;
  }

  public static void main(String[] args) {

  }
}
