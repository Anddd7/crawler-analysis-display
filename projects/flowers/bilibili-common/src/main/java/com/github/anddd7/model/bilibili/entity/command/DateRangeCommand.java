package com.github.anddd7.model.bilibili.entity.command;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
