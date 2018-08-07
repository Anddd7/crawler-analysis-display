package com.github.anddd7.analysis.bilibili.repository;

import com.github.anddd7.analysis.bilibili.repository.dao.PublishedDataDAO;
import com.github.anddd7.model.bilibili.domain.PublishedData;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class PublishedDataRepository {


  private final RedisTemplate redisTemplate;
  private final PublishedDataDAO publishedDataDAO;

  private final Lock lock = new ReentrantLock();

  @Autowired
  public PublishedDataRepository(
      RedisTemplate redisTemplate,
      PublishedDataDAO publishedDataDAO) {
    this.redisTemplate = redisTemplate;
    this.publishedDataDAO = publishedDataDAO;
  }

  public List<PublishedData> findByRecordTime(String recordTime) {
    ValueOperations ops = redisTemplate.opsForValue();
    String key = "bilibili.crawler.publisheddata." + recordTime;
    if (!redisTemplate.hasKey(key)) {
      lock.lock();
      if (!redisTemplate.hasKey(key)) {
        log.info("缓存失效未命中, 从数据库获取数据: {}", key);
        List<PublishedData> result = publishedDataDAO.findByRecordTimeOrderByCategoryId(recordTime);

        // TODO need config redis to mapping Object
        /*
        @Bean
        public RedisTemplate<String, Object> redisTemplate() {
            RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
            redisTemplate.setConnectionFactory(jedisConnectionFactory());
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
            return redisTemplate;
        }
         */
        ops.set(key, result, 60, TimeUnit.MINUTES);
      }
      lock.unlock();
    } else {
      log.info("缓存命中: {}", key);
    }
    return (List<PublishedData>) ops.get(key);
  }
}
