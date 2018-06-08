package com.github.anddd7.crawler.bilibili.client.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.anddd7.model.bilibili.entity.PageContainer;
import com.github.anddd7.model.bilibili.entity.VideoRecord;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchDataWrapper {

  private List<VideoData> result;
  private int pagesize;
  private String seid;
  private int page;
  /**
   * 请求类型, 本接口固定是search
   */
  @JsonIgnore
  private String rqt_type;
  private int numResults;
  private String code;
  /**
   * 性能指标, 暂不需要
   */
  @JsonIgnore
  private CostTime cost_time;
  private int numPages;
  private String msg;
  private String egg_hit;

  public PageContainer<VideoRecord> mappingToPageContainer() {
    if (Objects.isNull(result)) {
      result = Collections.emptyList();
    }
    return PageContainer.<VideoRecord>builder()
        .pageNumber(page)
        .pageSize(pagesize)
        .currentElements(result.size())
        .totalPages(numPages)
        .totalElements(numResults)
        .hasNext(page < numPages)
        .elements(
            result.stream()
                .map(VideoData::mappingToVideoRecord)
                .collect(Collectors.toList()))
        .build();
  }
}
