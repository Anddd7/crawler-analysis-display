package com.github.anddd7.crawler.bilibili.client.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.anddd7.crawler.bilibili.entity.PageContainer;
import com.github.anddd7.crawler.bilibili.entity.VideoRecord;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchDataWrapper {

  private List<VideoData> result;
  private String pagesize;
  private String seid;
  private String page;
  /**
   * 请求类型, 本接口固定是search
   */
  @JsonIgnore
  private String rqt_type;
  private String numResults;
  private String code;
  /**
   * 性能指标, 暂不需要
   */
  @JsonIgnore
  private CostTime cost_time;
  private String numPages;
  private String msg;
  private String egg_hit;

  public PageContainer<VideoRecord> mappingToPageContainer() {
    PageContainer pageContainer = PageContainer.<VideoRecord>builder()
        .pageNumber(Integer.valueOf(this.getPage()))
        .pageSize(Integer.valueOf(this.getPagesize()))
        .currentElements(this.getResult().size())
        .totalPages(Integer.valueOf(this.getNumPages()))
        .totalElements(Integer.valueOf(this.getNumResults()))
        .hasNext(this.getPage().equals(this.getNumPages()))
        .elements(
            this.getResult().stream().map(VideoData::mappingToVideoRecord).collect(Collectors.toList()))
        .build();
    return pageContainer;
  }
}
