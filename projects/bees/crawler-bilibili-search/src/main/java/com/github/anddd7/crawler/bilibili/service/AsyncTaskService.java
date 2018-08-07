package com.github.anddd7.crawler.bilibili.service;

import io.netty.util.concurrent.DefaultThreadFactory;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;

@Service
public class AsyncTaskService {

  private static final int SUBMITTED_POOL_SIZE = Runtime.getRuntime().availableProcessors();

  private final ExecutorService submittedExec;
  private final BlockingQueue<Runnable> submittedTasks;

  public AsyncTaskService() {
    this.submittedTasks = new LinkedBlockingQueue<>();
    this.submittedExec = new ThreadPoolExecutor(
        SUBMITTED_POOL_SIZE, SUBMITTED_POOL_SIZE,
        10L, TimeUnit.MINUTES,
        submittedTasks, getTrackingThreadFactory()
    );
  }

  private ThreadFactory getTrackingThreadFactory() {
    return new DefaultThreadFactory("AsyncTaskService");
  }

  public void execute(Runnable task) {
    execute(Collections.singletonList(task));
  }

  public void execute(List<Runnable> tasks) {
    for (Runnable task : tasks) {
      submittedExec.execute(task);
    }
  }
}
