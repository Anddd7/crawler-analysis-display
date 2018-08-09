package com.github.anddd7.model.bilibili.entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;

@Getter
public class Top10OfPublishedData {

  private final static Comparator<PublishedRecord> BY_TOTAL_PUBLISHED = Comparator
      .comparingInt(PublishedRecord::getPublishedVideos);
  private final static Comparator<PublishedRecord> BY_LAST_HALF_HOUR_PUBLISHED = Comparator
      .comparingInt(PublishedRecord::getNewVideosLastHalfHour);
  private final static Comparator<PublishedRecord> BY_LAST_HOUR_PUBLISHED = Comparator
      .comparingInt(PublishedRecord::getNewVideosLastHour);
  private final List<PublishedRecord> sortByCategory;
  private List<PublishedRecord> sortByTotalPublished;
  private List<PublishedRecord> sortByLastHalfHourPublished;
  private List<PublishedRecord> sortByLastHourPublished;

  public Top10OfPublishedData(List<PublishedRecord> sortByCategory) {
    this.sortByCategory = sortByCategory;
    this.sortByTotalPublished = new ArrayList<>(sortByCategory);
    this.sortByLastHalfHourPublished = new ArrayList<>(sortByCategory);
    this.sortByLastHourPublished = new ArrayList<>(sortByCategory);
    sort();
  }

  private void sort() {
    sortByTotalPublished.sort(BY_TOTAL_PUBLISHED);
    sortByTotalPublished = sortByTotalPublished.subList(0, 10);

    sortByLastHalfHourPublished.sort(BY_TOTAL_PUBLISHED);
    sortByLastHalfHourPublished = sortByLastHalfHourPublished.subList(0, 10);

    sortByLastHourPublished.sort(BY_TOTAL_PUBLISHED);
    sortByLastHourPublished = sortByLastHourPublished.subList(0, 10);
  }


}
