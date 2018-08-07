package com.github.anddd7.crawler.bilibili.service;

import com.github.anddd7.crawler.bilibili.client.RankClient;
import com.github.anddd7.crawler.bilibili.client.response.RankData;
import com.github.anddd7.crawler.bilibili.client.response.RankDataWrapper;
import com.github.anddd7.crawler.bilibili.repository.CategoryRepository;
import com.github.anddd7.crawler.bilibili.repository.RankRepository;
import com.github.anddd7.model.bilibili.entity.Category;
import com.github.anddd7.model.bilibili.entity.RankVideoRecord;
import java.util.Collections;
import java.util.List;
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

  public List<List<RankVideoRecord>> getRankBoard(int day) {
    return categoryRepository.getCategories()
        .stream()
        .map(category ->
            mappingToRankVideoRecord(
                category,
                rankClient.get(category.getCategoryId(), day, 0)
            )
        )
        .collect(Collectors.toList());
  }

  private List<RankVideoRecord> mappingToRankVideoRecord(Category category,
      RankDataWrapper rankDataWrapper) {
    List<RankData> result = rankDataWrapper.getData();
    if (result == null) {
      return Collections.emptyList();
    }
    return result.stream().map(
        rankData -> RankVideoRecord.builder()
            .categoryId(category.getCategoryId())
            .categoryName(category.getDescription())
            .title(rankData.getTitle())
            .description(rankData.getDescription())
            .author(rankData.getAuthor())
            .pic(rankData.getPic())
            .pts(rankData.getPts())
            .coins(rankData.getCoins())
            .favoriteCount(rankData.getFavorites())
            .playCount(rankData.getPlay())
            .commentCount(rankData.getReview())
            .bulletCount(rankData.getVideo_review())
            .build()
    ).collect(Collectors.toList());
  }

}