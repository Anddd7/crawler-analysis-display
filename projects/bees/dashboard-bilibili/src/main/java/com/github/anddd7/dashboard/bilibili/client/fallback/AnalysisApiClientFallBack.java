package com.github.anddd7.dashboard.bilibili.client.fallback;

import com.github.anddd7.dashboard.bilibili.client.AnalysisApiClient;
import com.github.anddd7.model.bilibili.entity.PublishedRecord;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.NotImplementedException;

@Slf4j
public class AnalysisApiClientFallBack implements AnalysisApiClient {

  private final Throwable cause;

  public AnalysisApiClientFallBack(Throwable cause) {
    this.cause = cause;
  }

  @Override
  public List<PublishedRecord> getCalculatedPublishedRecord() {
    log.error("[getCalculatedPublishedRecord] failed, caused by :{}", cause.getMessage());
    throw new NotImplementedException();
  }
}
