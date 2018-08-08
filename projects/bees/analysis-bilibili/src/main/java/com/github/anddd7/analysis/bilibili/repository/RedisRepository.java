package com.github.anddd7.analysis.bilibili.repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class RedisRepository {

  private final RedisTemplate<String, Object> objectRedisTemplate;
  private final Lock lock = new ReentrantLock();

  @Autowired
  public RedisRepository(RedisTemplate<String, Object> objectRedisTemplate) {
    this.objectRedisTemplate = objectRedisTemplate;
  }

  public <T> T findByKey(String key, Supplier<Optional<T>> supplier) {
    ValueOperations<String, Object> ops = objectRedisTemplate.opsForValue();
    if (!Optional.ofNullable(objectRedisTemplate.hasKey(key)).orElse(false)) {
      lock.lock();
      if (!Optional.ofNullable(objectRedisTemplate.hasKey(key)).orElse(false)) {
        log.info("缓存失效未命中, 从数据库获取数据: {}", key);
        supplier.get()
            .ifPresent(result ->
                ops.set(key, result, 60, TimeUnit.MINUTES)
            );
      }
      lock.unlock();
    } else {
      log.info("缓存命中: {}", key);
    }
    return (T) ops.get(key);
  }
}
