package github.eddy.bigdata.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateTools {

  static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
  static final SimpleDateFormat yyyyMM = new SimpleDateFormat("yyyyMM");

  public static String[] getDateRangeYYYYMMDD(Integer year, Integer month) {
    LocalDate startDay = LocalDate.of(year, month, 1);
    LocalDate endDay = startDay.with(TemporalAdjusters.lastDayOfMonth());
    return new String[]{yyyyMMdd.format(startDay), yyyyMMdd.format(endDay)};
  }

  public static String getYYYYDD(Date date) {
    return yyyyMM.format(date);
  }
}
