package com.github.anddd7.analysis.bilibili.repository;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * Test multiple thread program
 * - mock object
 * - verify method
 * - captor parameters
 * - mock logger and verify logs
 */
@RunWith(MockitoJUnitRunner.class)
public class RedisRepositoryTest {

  private final static int NUM_THREAD = 2;
  private final ExecutorService exec = Executors.newFixedThreadPool(NUM_THREAD);
  private final Object expectedNewed = new Object();
  private final Object expectedCached = new Object();
  @InjectMocks
  private RedisRepository redisRepository;
  @Mock
  private RedisTemplate<String, Object> mockRedisTemplate;
  @Mock
  private ValueOperations<String, Object> mockOps;
  @Mock
  private Supplier<Optional<Object>> mockSupplier;
  @Mock
  private Appender<ILoggingEvent> mockAppender;
  @Captor
  private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

  @Before
  public void setUp() throws Exception {
    final Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    root.addAppender(mockAppender);
  }

  @After
  public void teardown() {
    final Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    root.detachAppender(mockAppender);
  }

  @Test
  public void noWaitForLock_WhenCacheHit() {
    when(mockRedisTemplate.hasKey(eq("cached"))).thenReturn(true);
    when(mockRedisTemplate.opsForValue()).thenReturn(mockOps);
    when(mockOps.get("cached")).thenReturn(expectedCached);

    Object result = redisRepository.findByKey("cached", mockSupplier);

    // result
    verify(mockSupplier, never()).get();
    Assert.assertEquals(result, expectedCached);

    // logging
    verify(mockAppender).doAppend(captorLoggingEvent.capture());
    LoggingEvent loggingEvent = captorLoggingEvent.getValue();
    assertThat(loggingEvent.getLevel(), is(Level.INFO));
    assertThat(loggingEvent.getFormattedMessage(), is("缓存命中: cached"));
  }

  @Test
  public void waitForLock_WhenCacheMissed() {
    when(mockRedisTemplate.hasKey(eq("newed"))).thenReturn(false);
    when(mockRedisTemplate.opsForValue()).thenReturn(mockOps);
    when(mockOps.get("newed")).thenReturn(expectedNewed);
    when(mockSupplier.get()).thenReturn(Optional.of(expectedNewed));

    Object result = redisRepository.findByKey("newed", mockSupplier);

    // result
    verify(mockSupplier, times(1)).get();
    verify(mockOps, times(1)).set(eq("newed"), eq(expectedNewed), eq(60L), eq(TimeUnit.MINUTES));
    Assert.assertEquals(result, expectedNewed);

    // logging
    verify(mockAppender, times(3)).doAppend(captorLoggingEvent.capture());
    List<LoggingEvent> loggingEvents = captorLoggingEvent.getAllValues();
    assertTrue(loggingEvents.get(0).getFormattedMessage().matches("进入\\[.*\\]锁控制区域"));
    assertThat(loggingEvents.get(1).getFormattedMessage(), is("缓存失效未命中, 从数据库获取数据: newed"));
    assertTrue(loggingEvents.get(2).getFormattedMessage().matches("离开\\[.*\\]锁控制区域"));
  }

  @Test
  public void waitForSameLock_WhenCacheMissedWithSameKey() throws InterruptedException {
    when(mockRedisTemplate.hasKey(eq("same"))).thenReturn(false);
    when(mockRedisTemplate.opsForValue()).thenReturn(mockOps);
    when(mockOps.get("same")).thenReturn(expectedNewed);
    when(mockSupplier.get()).thenReturn(Optional.of(expectedNewed));

    // multi thread call
    for (int i = 0; i < NUM_THREAD; i++) {
      exec.submit(() -> redisRepository.findByKey("same", mockSupplier));
    }
    exec.shutdown();
    while (!exec.awaitTermination(1, TimeUnit.MINUTES)) {
      // wait
    }

    // result
    verify(mockSupplier, times(2)).get();
    verify(mockOps, times(2)).set(eq("same"), eq(expectedNewed), eq(60L), eq(TimeUnit.MINUTES));

    // logging
    verify(mockAppender, times(6)).doAppend(captorLoggingEvent.capture());
    List<LoggingEvent> loggingEvents = captorLoggingEvent.getAllValues();
    assertTrue(loggingEvents.get(0).getFormattedMessage().matches("进入\\[.*\\]锁控制区域"));
    assertThat(loggingEvents.get(1).getFormattedMessage(), is("缓存失效未命中, 从数据库获取数据: same"));
    assertTrue(loggingEvents.get(2).getFormattedMessage().matches("离开\\[.*\\]锁控制区域"));
    assertTrue(loggingEvents.get(3).getFormattedMessage().matches("进入\\[.*\\]锁控制区域"));
    assertThat(loggingEvents.get(4).getFormattedMessage(), is("缓存失效未命中, 从数据库获取数据: same"));
    assertTrue(loggingEvents.get(5).getFormattedMessage().matches("离开\\[.*\\]锁控制区域"));
  }

  @Test
  public void waitForDifferentLock_WhenCacheMissedWithDifferentKey() throws InterruptedException {
    when(mockRedisTemplate.hasKey(anyString())).thenReturn(false);
    when(mockRedisTemplate.opsForValue()).thenReturn(mockOps);
    when(mockOps.get(anyString())).thenReturn(expectedNewed);
    when(mockSupplier.get()).thenReturn(Optional.of(expectedNewed));

    // multi thread call
    for (int i = 0; i < NUM_THREAD; i++) {
      final int j = i;
      exec.submit(() -> redisRepository.findByKey("different-" + j, mockSupplier));
    }
    exec.shutdown();
    while (!exec.awaitTermination(1, TimeUnit.MINUTES)) {
      // wait
    }

    // result
    verify(mockSupplier, times(2)).get();
    verify(mockOps, times(2)).set(anyString(), eq(expectedNewed), eq(60L), eq(TimeUnit.MINUTES));

    // logging
    verify(mockAppender, times(6)).doAppend(captorLoggingEvent.capture());
    List<LoggingEvent> loggingEvents = captorLoggingEvent.getAllValues();
    assertTrue(loggingEvents.get(0).getFormattedMessage().matches("进入\\[.*\\]锁控制区域"));
    assertTrue(loggingEvents.get(1).getFormattedMessage().matches("进入\\[.*\\]锁控制区域"));
    assertTrue(
        loggingEvents.get(2).getFormattedMessage().matches("缓存失效未命中, 从数据库获取数据: different-\\d"));
    assertTrue(
        loggingEvents.get(3).getFormattedMessage().matches("缓存失效未命中, 从数据库获取数据: different-\\d"));
    assertTrue(loggingEvents.get(4).getFormattedMessage().matches("离开\\[.*\\]锁控制区域"));
    assertTrue(loggingEvents.get(5).getFormattedMessage().matches("离开\\[.*\\]锁控制区域"));
  }
}