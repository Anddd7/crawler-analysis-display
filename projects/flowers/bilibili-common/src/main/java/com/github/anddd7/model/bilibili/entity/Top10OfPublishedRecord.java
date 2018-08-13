package com.github.anddd7.model.bilibili.entity;

import com.github.anddd7.util.FunctionTool;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;

@Getter
public class Top10OfPublishedRecord {

  private final static Comparator<PublishedRecord> BY_TOTAL_PUBLISHED = FunctionTool
      .comparingIntDesc(PublishedRecord::getPublishedVideos);
  private final static Comparator<PublishedRecord> BY_LAST_HALF_HOUR_PUBLISHED = FunctionTool
      .comparingIntDesc(PublishedRecord::getNewVideosLastHalfHour);
  private final static Comparator<PublishedRecord> BY_LAST_HOUR_PUBLISHED = FunctionTool
      .comparingIntDesc(PublishedRecord::getNewVideosLastHour);

  private List<PublishedRecord> sortByCategory;
  private List<PublishedRecord> sortByTotalPublished;
  private List<PublishedRecord> sortByLastHalfHourPublished;
  private List<PublishedRecord> sortByLastHourPublished;

  public Top10OfPublishedRecord(List<PublishedRecord> sortByCategory) {
    this.sortByCategory = sortByCategory;
    this.sortByTotalPublished = new ArrayList<>(sortByCategory);
    this.sortByLastHalfHourPublished = new ArrayList<>(sortByCategory);
    this.sortByLastHourPublished = new ArrayList<>(sortByCategory);
    this.sort();
  }

  private void sort() {
    sortByTotalPublished.sort(BY_TOTAL_PUBLISHED);
    sortByTotalPublished = sortByTotalPublished.subList(
        0,
        Math.min(sortByTotalPublished.size(), 10)
    );

    sortByLastHalfHourPublished.sort(BY_TOTAL_PUBLISHED);
    sortByLastHalfHourPublished = sortByLastHalfHourPublished.subList(
        0,
        Math.min(sortByLastHalfHourPublished.size(), 10)
    );

    sortByLastHourPublished.sort(BY_TOTAL_PUBLISHED);
    sortByLastHourPublished = sortByLastHourPublished.subList(
        0,
        Math.min(sortByLastHourPublished.size(), 10)
    );
  }
}
