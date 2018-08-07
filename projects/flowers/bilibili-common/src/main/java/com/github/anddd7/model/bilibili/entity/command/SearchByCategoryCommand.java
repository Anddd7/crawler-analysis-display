package com.github.anddd7.model.bilibili.entity.command;


import com.github.anddd7.util.DateTool;
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
public class SearchByCategoryCommand {

  private int categoryId;
  private int pageSize;
  private int pageNumber;
  private DateRangeCommand dateRange;

  public String getFromDate() {
    return dateRange.getFromDate().format(DateTool.DATE_NO_SEPARATOR);
  }

  public String getToDate() {
    return dateRange.getToDate().format(DateTool.DATE_NO_SEPARATOR);
  }
}
