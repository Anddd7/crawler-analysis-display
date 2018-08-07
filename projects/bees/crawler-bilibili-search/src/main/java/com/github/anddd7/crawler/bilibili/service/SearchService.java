package com.github.anddd7.crawler.bilibili.service;

import static com.github.anddd7.util.DateTool.DATE_NO_SEPARATOR;

import com.github.anddd7.crawler.bilibili.client.SearchClient;
import com.github.anddd7.crawler.bilibili.client.response.SearchDataWrapper;
import com.github.anddd7.crawler.bilibili.client.response.VideoData;
import com.github.anddd7.crawler.bilibili.repository.CategoryRepository;
import com.github.anddd7.model.bilibili.constant.CopyRight;
import com.github.anddd7.model.bilibili.constant.Order;
import com.github.anddd7.model.bilibili.domain.PublishedData;
import com.github.anddd7.model.bilibili.entity.Category;
import com.github.anddd7.model.bilibili.entity.PageContainer;
import com.github.anddd7.model.bilibili.entity.VideoRecord;
import com.github.anddd7.model.bilibili.entity.command.SearchByCategoryCommand;
import com.github.anddd7.util.DateTool;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {


  /**
   * 固定参数
   */
  private static final String MAIN_VERSION = "v3";
  private static final String SEARCH_TYPE = "video";
  private static final String VIEW_TYPE = "hot_rank";
  private static final String PIC_SIZE = "160x100";

  private final SearchClient searchClient;
  private final CategoryRepository categoryRepository;

  @Autowired
  public SearchService(final SearchClient searchClient,
      CategoryRepository categoryRepository) {
    this.searchClient = searchClient;
    this.categoryRepository = categoryRepository;
  }

  /**
   * --------------
   * Runtime method
   * --------------
   */

  public PageContainer<VideoRecord> getTodayVideosByCategory(SearchByCategoryCommand command) {
    return packageVideoRecord(
        searchClient.search(
            MAIN_VERSION,
            SEARCH_TYPE,
            VIEW_TYPE,
            PIC_SIZE,
            Order.click.name(),
            CopyRight.ALL.getCode(),
            command.getPageSize(),
            command.getPageNumber(),
            command.getCategoryId(),
            command.getFromDate(),
            command.getToDate()
        )
    );
  }

  public List<PublishedData> getCurrentPublishedData() {
    LocalDateTime recordTime = LocalDateTime.now();
    return categoryRepository.getCategories()
        .stream()
        .map(category -> getCurrentPublishedVideos(recordTime, category))
        .collect(Collectors.toList());
  }


  PublishedData getCurrentPublishedVideos(LocalDateTime recordTime, Category category) {
    return mappingToPublishedData(
        recordTime, category,
        searchClient.search(
            MAIN_VERSION,
            SEARCH_TYPE,
            VIEW_TYPE,
            PIC_SIZE,
            Order.click.name(),
            CopyRight.ALL.getCode(),
            1, 1,
            category.getCategoryId(),
            recordTime.format(DATE_NO_SEPARATOR),
            recordTime.format(DATE_NO_SEPARATOR)
        )
    );
  }


  /**
   * --------------
   * mapping method
   * --------------
   */

  private PageContainer<VideoRecord> packageVideoRecord(SearchDataWrapper searchDataWrapper) {
    List<VideoData> result = searchDataWrapper.getResult();
    if (result == null) {
      result = Collections.emptyList();
    }
    return PageContainer.<VideoRecord>builder()
        .pageNumber(searchDataWrapper.getPage())
        .pageSize(searchDataWrapper.getPagesize())
        .currentElements(result.size())
        .totalPages(searchDataWrapper.getNumPages())
        .totalElements(searchDataWrapper.getNumResults())
        .hasNext(searchDataWrapper.getPage() < searchDataWrapper.getNumPages())
        .elements(
            result.stream()
                .map(this::mappingToVideoRecord)
                .collect(Collectors.toList()))
        .build();
  }

  private VideoRecord mappingToVideoRecord(VideoData videoData) {
    return VideoRecord.builder()
        .createTime(Long.valueOf(videoData.getSenddate()))
        .publishDate(LocalDateTime.parse(videoData.getPubdate(), DateTool.DATE_TIME))
        .tags(Arrays.asList(videoData.getTag().trim().split(",")))
        .playCount(Integer.valueOf(videoData.getPlay()))
        .favoriteCount(Integer.valueOf(videoData.getFavorites()))
        .picUrl(videoData.getPic())
        .id(videoData.getId())
        .author(videoData.getAuthor())
        .duration(Integer.valueOf(videoData.getDuration()))
        .title(videoData.getTitle())
        .bulletCount(Integer.valueOf(videoData.getVideo_review()))
        .url(videoData.getArcurl())
        .description(videoData.getDescription())
        .commentCount(Integer.valueOf(videoData.getReview()))
        .build();
  }

  PublishedData mappingToPublishedData(
      LocalDateTime recordTime, Category category, SearchDataWrapper wrapper) {
    return PublishedData.builder()
        .categoryId(category.getCategoryId())
        .categoryName(category.getDescription())
        .publishedVideos(wrapper.getNumResults())
        .recordTime(recordTime.format(DateTool.DATE_TIME_IGNORE_SEC))
        .build();
  }
}
