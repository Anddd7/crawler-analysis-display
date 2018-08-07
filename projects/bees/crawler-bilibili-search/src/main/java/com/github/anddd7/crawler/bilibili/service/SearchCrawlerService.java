package com.github.anddd7.crawler.bilibili.service;

import com.github.anddd7.crawler.bilibili.repository.CategoryRepository;
import com.github.anddd7.crawler.bilibili.repository.PublishedDataRepository;
import com.github.anddd7.model.bilibili.entity.Category;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * --------------
 * Background method
 * --------------
 */
@Slf4j
@Service
public class SearchCrawlerService {

  private final SearchService searchService;
  private final AsyncTaskService asyncTaskService;
  private final CategoryRepository categoryRepository;
  private final PublishedDataRepository publishedDataRepository;

  @Autowired
  public SearchCrawlerService(
      final SearchService searchService,
      AsyncTaskService asyncTaskService,
      CategoryRepository categoryRepository,
      PublishedDataRepository publishedDataRepository) {
    this.searchService = searchService;
    this.asyncTaskService = asyncTaskService;
    this.categoryRepository = categoryRepository;
    this.publishedDataRepository = publishedDataRepository;
  }


  /**
   * 每5分钟抓取一次当前各类别的视频发布数
   */
  @Scheduled(cron = "0 */5 * * * ?")
  public void storeCurrentPublishedData() {
    LocalDateTime recordTime = LocalDateTime.now();

    List<Category> categories = categoryRepository.getCategories();
    final CountDownLatch working = new CountDownLatch(categories.size());

    Runnable trackingTask = () -> {
      try {
        log.info("Start to store today's published data");
        working.await();
        log.info("Finished storing today's published data");
      } catch (InterruptedException e) {
        // 不需要主动中断, 忽略
      }
    };

    List<Runnable> workingTasks = categories
        .stream()
        .map(category -> (Runnable) () -> {
              try {
                publishedDataRepository.insert(
                    searchService.getCurrentPublishedVideos(recordTime, category)
                );
              } finally {
                working.countDown();
              }
            }
        )
        .collect(Collectors.toList());

    asyncTaskService.execute(trackingTask);
    asyncTaskService.execute(workingTasks);
  }
}
