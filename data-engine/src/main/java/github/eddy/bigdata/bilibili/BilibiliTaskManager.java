package github.eddy.bigdata.bilibili;


import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import github.eddy.bigdata.bilibili.common.TaskStatusEnum;
import github.eddy.bigdata.bilibili.task.BilibiliAnalysis;
import github.eddy.bigdata.bilibili.task.BilibiliCrawler;
import github.eddy.bigdata.core.beans.KnownException;
import github.eddy.bigdata.core.dao.MongodbDao;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static github.eddy.bigdata.bilibili.common.DBTableEnum.TASK_RECORD;
import static github.eddy.bigdata.bilibili.common.TaskStatusEnum.*;
import static java.time.Instant.now;

/**
 * @author edliao
 * bilibili相关任务的启动和管理
 */
@Slf4j
public class BilibiliTaskManager {

    private BilibiliCrawler bilibiliCrawler = new BilibiliCrawler();
    private BilibiliAnalysis bilibiliAnalysis = new BilibiliAnalysis();
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    private void execute(String module, String taskName, List<Object> params) {
        BasicDBObject searchQuery = new BasicDBObject()
                .append("module", module)
                .append("taskName", taskName)
                .append("params", params);

        log.debug("开始执行任务:\n{}", searchQuery.toString());

        try (MongoCursor<Document> result = MongodbDao.find(TASK_RECORD, searchQuery)) {
            if (result.hasNext()) {
                Document taskRecord = result.next();

                log.debug("查询到任务历史记录:\n{}", taskRecord.toString());

                TaskStatusEnum taskStatus = TaskStatusEnum.valueOf(taskRecord.getString("taskStatus"));
                if (taskStatus.equals(running)) {
                    throw new KnownException(taskRecord.toString() + "任务正在执行中 ,请勿重复执行");
                } else if (taskStatus.equals(TaskStatusEnum.finished)) {
                    throw new KnownException(taskRecord.toString() + "任务已执行完毕 ,请到结果页面进行查看");
                }
            }
        }

        Instant start = now();
        searchQuery.append("taskStatus", running);
        searchQuery.append("startTime", start);
        MongodbDao.insert(TASK_RECORD, searchQuery);

        executorService.execute(() -> {
            TaskStatusEnum endStatus = finished;
            try {
                router(module, taskName, params);
            } catch (Exception e) {
                endStatus = stop;
                log.error("{}任务执行异常中止", String.join(module, taskName), e);
            }

            log.debug("{}任务执行完毕: {} ms", String.join(module, taskName), Duration.between(start, now()).toMillis());
            MongodbDao.update(TASK_RECORD, searchQuery,
                    new BasicDBObject()
                            .append("$set", new BasicDBObject()
                                    .append("taskStatus", endStatus)
                                    .append("endTime", now())));
        });
    }


    public void router(String module, String taskName, List<Object> params) {
        switch (module) {
            case "crawler":
                routerCrawler(taskName, params);
                break;
            case "analysis":
                routerAnalysis(taskName, params);
                break;
            default:
        }
    }

    public void routerCrawler(String taskName, List<Object> params) {
        switch (taskName) {
            case "search":
                bilibiliCrawler.search((Integer) params.get(0), (Integer) params.get(1));
                break;
            default:
        }
    }

    public void routerAnalysis(String taskName, List<Object> params) {
        switch (taskName) {
            case "tagsplit":
                bilibiliAnalysis.tagSplit((Integer) params.get(0), (Integer) params.get(1));
                break;
            case "categorydata":
                bilibiliAnalysis.categoryData((Integer) params.get(0), (Integer) params.get(1));
                break;
            default:
        }
    }

    public static void main(String[] a) {
        BilibiliTaskManager taskManager = new BilibiliTaskManager();
//        taskManager.execute("crawler", "search", Arrays.asList(2017, 7));
//        taskManager.execute("crawler", "search", Arrays.asList(2017, 8));
        taskManager.execute("analysis", "tagsplit", Arrays.asList(2017, 7));
        taskManager.execute("analysis", "tagsplit", Arrays.asList(2017, 8));
        taskManager.execute("analysis", "categorydata", Arrays.asList(2017, 7));
        taskManager.execute("analysis", "categorydata", Arrays.asList(2017, 8));
    }
}