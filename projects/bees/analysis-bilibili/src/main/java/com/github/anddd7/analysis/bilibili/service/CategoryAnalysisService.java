package com.github.anddd7.analysis.bilibili.service;

import com.github.anddd7.analysis.bilibili.client.SearchApiClient;
import com.github.anddd7.analysis.bilibili.repository.PublishedDataRepository;
import com.github.anddd7.model.bilibili.domain.PublishedData;
import com.github.anddd7.model.bilibili.entity.PublishedRecord;
import com.github.anddd7.util.DateTool;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryAnalysisService {

  private final SearchApiClient searchApiClient;
  private final PublishedDataRepository publishedDataRepository;

  @Autowired
  public CategoryAnalysisService(
      SearchApiClient searchApiClient,
      PublishedDataRepository publishedDataRepository) {
    this.searchApiClient = searchApiClient;
    this.publishedDataRepository = publishedDataRepository;
  }

  public List<PublishedRecord> getCalculatedPublishedRecord() {
    LocalDateTime localDateTime = LocalDateTime.now();
    LocalDateTime latestRecordTime = localDateTime.minusMinutes(localDateTime.getMinute() % 5);

    List<PublishedData> latestPublishedData = publishedDataRepository
        .findByRecordTime(latestRecordTime.format(DateTool.DATE_TIME_IGNORE_SEC));

    List<PublishedData> lastHalfHourPublishedData = publishedDataRepository
        .findByRecordTime(latestRecordTime.minusMinutes(30).format(DateTool.DATE_TIME_IGNORE_SEC));

    List<PublishedData> lastHourPublishedData = publishedDataRepository
        .findByRecordTime(latestRecordTime.minusMinutes(60).format(DateTool.DATE_TIME_IGNORE_SEC));

    List<PublishedRecord> publishedRecords = new ArrayList<>();
    for (int i = 0; i < latestPublishedData.size(); i++) {
      PublishedData latest = latestPublishedData.get(i);

      int lastHalfHourPublished = 0;
      if (i < lastHalfHourPublishedData.size()) {
        lastHalfHourPublished =
            latest.getPublishedVideos() - lastHalfHourPublishedData.get(i).getPublishedVideos();
      }

      int lastHourPublished = 0;
      if (i < lastHourPublishedData.size()) {
        lastHourPublished =
            latest.getPublishedVideos() - lastHourPublishedData.get(i).getPublishedVideos();
      }

      publishedRecords.add(
          PublishedRecord.builder()
              .categoryId(latest.getCategoryId())
              .categoryName(latest.getCategoryName())
              .publishedVideos(latest.getPublishedVideos())
              .newVideosLastHalfHour(lastHalfHourPublished)
              .newVideosLastHour(lastHourPublished)
              .build()
      );
    }
    return publishedRecords;
  }
}
