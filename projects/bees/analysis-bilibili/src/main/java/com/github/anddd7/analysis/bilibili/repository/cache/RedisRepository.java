package com.github.anddd7.analysis.bilibili.repository.cache;

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
  private final static int NUM_LOCK = 16;
  private final Lock[] locks;

  @Autowired
  public RedisRepository(RedisTemplate<String, Object> objectRedisTemplate) {
    this.objectRedisTemplate = objectRedisTemplate;
    this.locks = new Lock[NUM_LOCK];
    for (int i = 0; i < locks.length; i++) {
      locks[i] = new ReentrantLock();
    }
  }

  public <T> T replace(String key, T value) {
    return (T) objectRedisTemplate.opsForValue().getAndSet(key, value);
  }


  public <T> T get(String key, Supplier<Optional<T>> supplier) {
    ValueOperations<String, Object> ops = objectRedisTemplate.opsForValue();
    if (!Optional.ofNullable(objectRedisTemplate.hasKey(key)).orElse(false)) {
      Lock lock = indexOf(key);
      lock.lock();
      log.info("进入[{}]锁控制区域", Integer.toHexString(lock.hashCode()));
      try {
        if (!Optional.ofNullable(objectRedisTemplate.hasKey(key)).orElse(false)) {
          log.info("缓存失效未命中, 从数据库获取数据: {}", key);
          supplier.get()
              .ifPresent(result ->
                  ops.set(key, result, 60, TimeUnit.MINUTES)
              );
        }
      } finally {
        log.info("离开[{}]锁控制区域", Integer.toHexString(lock.hashCode()));
        lock.unlock();
      }
    } else {
      log.info("缓存命中: {}", key);
    }
    return (T) ops.get(key);
  }

  private Lock indexOf(String key) {
    return locks[key.hashCode() & (NUM_LOCK - 1)];
  }
}
