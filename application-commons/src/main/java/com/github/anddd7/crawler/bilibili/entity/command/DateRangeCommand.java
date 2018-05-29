package com.github.anddd7.crawler.bilibili.entity.command;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class DateRangeCommand {

  private LocalDate fromDate;
  private LocalDate toDate;

  public static DateRangeCommand today() {
    return DateRangeCommand.builder()
        .fromDate(LocalDate.now())
        .toDate(LocalDate.now())
        .build();
  }
}
