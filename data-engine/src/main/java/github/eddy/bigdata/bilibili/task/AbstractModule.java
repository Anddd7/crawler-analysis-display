package github.eddy.bigdata.bilibili.task;

import static github.eddy.bigdata.bilibili.common.DBTableEnum.sys_task_record;
import static github.eddy.bigdata.bilibili.common.TaskStatusEnum.running;
import static java.time.Instant.now;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import github.eddy.bigdata.bilibili.common.TaskStatusEnum;
import github.eddy.bigdata.core.beans.KnownException;
import github.eddy.bigdata.core.mongo.MongoDao;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

/**
 * @author edliao
 * @since 11/15/2017 模块路由
 */
@Slf4j
public abstract class AbstractModule {

  private ExecutorService threadPool = Executors.newFixedThreadPool(10);
  final MongoDao dao = new MongoDao("bilibili");


  /**
   * 获取模块名
   */
  public String getModuleName() {
    String className = this.getClass().getSimpleName().toLowerCase();
    return className.substring(0, className.length() - 6);
  }

  /**
   * 执行任务
   */
  public void execute(String taskName, List<Object> params) {
    BasicDBObject taskModel = new BasicDBObject()
        .append("module", getModuleName())
        .append("taskName", taskName)
        .append("params", params);

    taskStart(taskModel);
    threadPool.execute(() -> {
      router(taskName, params);
      taskStart(taskModel);
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
    log.debug("{}任务执行完毕", JSON.toJSONString(taskModel.toMap()),
        Duration.between((Instant) taskModel.get("startTime"), end).toMillis());
  }

  /**
   * 任务子路由
   */
  abstract void router(String taskName, List<Object> params);
}
