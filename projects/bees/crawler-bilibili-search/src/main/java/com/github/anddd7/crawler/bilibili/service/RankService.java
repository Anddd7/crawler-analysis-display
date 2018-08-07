package com.github.anddd7.crawler.bilibili.service;

import com.github.anddd7.crawler.bilibili.client.RankClient;
import com.github.anddd7.crawler.bilibili.client.response.RankData;
import com.github.anddd7.crawler.bilibili.repository.CategoryRepository;
import com.github.anddd7.crawler.bilibili.repository.RankRepository;
import com.github.anddd7.model.bilibili.entity.Category;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RankService {

  private final RankClient rankClient;
  private final CategoryRepository categoryRepository;

  @Autowired
  public RankService(
      RankClient rankClient,
      CategoryRepository categoryRepository,
      RankRepository rankRepository,
      AsyncTaskService asyncTaskService) {
    this.rankClient = rankClient;
    this.categoryRepository = categoryRepository;
  }

  /**
   * --------------
   * Runtime method
   * --------------
   */

  public Map<Category, List<RankData>> getTodayRankBoard() {
    return categoryRepository.getCategories()
        .stream()
        .collect(
            Collectors.toMap(
                Function.identity(),
                category -> rankClient.get(category.getCategoryId(), 1, 0).getData()
            )
        );
  }
}