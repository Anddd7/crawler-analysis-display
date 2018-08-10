package com.github.anddd7.analysis.bilibili.repository;

import com.github.anddd7.analysis.bilibili.repository.cache.RedisRepository;
import com.github.anddd7.analysis.bilibili.repository.dao.PublishedDataDAO;
import com.github.anddd7.model.bilibili.domain.PublishedData;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class PublishedDataRepository {

  private final PublishedDataDAO publishedDataDAO;
  private final RedisRepository redisRepository;

  @Autowired
  public PublishedDataRepository(
      PublishedDataDAO publishedDataDAO,
      RedisRepository redisRepository) {
    this.publishedDataDAO = publishedDataDAO;
    this.redisRepository = redisRepository;
  }

  public List<PublishedData> findByRecordTime(String recordTime) {
    final String key = "bilibili.crawler.publisheddata." + recordTime;
    return redisRepository.get(
        key,
        () -> {
          List<PublishedData> result = publishedDataDAO
              .findByRecordTimeOrderByCategoryId(recordTime);
          return result == null || result.isEmpty() ? Optional.empty() : Optional.of(result);
        }
    );
  }
}
