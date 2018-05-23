package com.github.anddd7.crawler.bilibili.controller.command;

import com.github.anddd7.boot.utils.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class SearchByCategoryCommand {


  private String categoryId;
  private int pageSize;
  private int pageNumber;
  private DateRangeCommand dateRange;

  public String getFromDate() {
    return dateRange.getFromDate().format(Constants.DATE_NO_SEPARATOR);
  }

  public String getToDate() {
    return dateRange.getToDate().format(Constants.DATE_NO_SEPARATOR);
  }
}
