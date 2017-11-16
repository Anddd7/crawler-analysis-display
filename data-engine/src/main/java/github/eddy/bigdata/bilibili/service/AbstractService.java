package github.eddy.bigdata.bilibili.service;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;
import static github.eddy.bigdata.bilibili.common.DBTableEnum.sys_task_record;
import static github.eddy.bigdata.bilibili.common.TaskStatusEnum.finished;
import static github.eddy.bigdata.bilibili.common.TaskStatusEnum.running;
import static github.eddy.bigdata.bilibili.common.TaskStatusEnum.stop;
import static java.time.Instant.now;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import github.eddy.bigdata.bilibili.common.TaskStatusEnum;
import github.eddy.bigdata.bilibili.dao.BilibiliDao;
import github.eddy.bigdata.bilibili.service.common.TaskParams;
import github.eddy.bigdata.core.beans.KnownException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author edliao
 * @since 11/15/2017 模块路由
 */
@Slf4j
public abstract class AbstractService {

  @Autowired
  BilibiliDao dao;

  private ExecutorService threadPool = Executors.newFixedThreadPool(10);

  /**
   * 获取模块名
   */
  private String getServiceName() {
    String className = this.getClass().getSimpleName();
    return UPPER_CAMEL.to(LOWER_CAMEL, className.substring(0, className.length() - 7));
  }

  /**
   * 执行任务
   */
  public void execute(String taskName, TaskParams taskParams) {
    Method method = router(taskName);

    BasicDBObject taskModel = new BasicDBObject()
        .append("serviceName", getServiceName())
        .append("taskName", taskName)
        .append("params", taskParams.toMap());

    taskStart(taskModel);
    threadPool.execute(() -> {
      TaskStatusEnum endStatus = finished;
      try {
        method.invoke(this, taskParams);
      } catch (Exception e) {
        endStatus = stop;
        log.error("", e);
        throw new KnownException("任务" + taskName + "执行失败", e);
      } finally {
        taskEnd(taskModel, endStatus);
        log.debug("{}任务执行完毕", JSON.toJSONString(taskModel.toMap()));
      }
    });
  }

  /**
   * 任务开始记录
   */
  private void taskStart(BasicDBObject taskModel) {
    MongoCursor<Document> history = dao.find(sys_task_record.name(), taskModel);
    if (history.hasNext()) {
      Document taskRecord = history.next();
      log.debug("查询到任务历史记录:\n{}", taskRecord.toString());
      TaskStatusEnum taskStatus = TaskStatusEnum.valueOf(taskRecord.getString("taskStatus"));
      if (taskStatus.equals(running)) {
        throw new KnownException(taskRecord.toString() + "任务正在执行中 ,请勿重复执行");
      } else if (taskStatus.equals(TaskStatusEnum.finished)) {
        throw new KnownException(taskRecord.toString() + "任务已执行完毕 ,请到结果页面进行查看");
      }
    }

    taskModel.append("taskStatus", running);
    taskModel.append("startTime", now());
    dao.insert(sys_task_record.name(), taskModel);
  }

  /**
   * 任务结束记录
   */
  private void taskEnd(BasicDBObject taskModel, TaskStatusEnum endStatus) {
    Instant end = now();
    dao.update(sys_task_record.name(), taskModel,
        new BasicDBObject().append("$set",
            new BasicDBObject().append("taskStatus", endStatus).append("endTime", end)));
  }

  /**
   * 任务子路由
   */
  private Method router(String taskName) {
    Class clazz = this.getClass();
    try {
      return clazz.getMethod(taskName, TaskParams.class);
    } catch (NoSuchMethodException e) {
      log.error("", e);
      throw new KnownException("任务" + taskName + "不存在", e);
    }
  }
}