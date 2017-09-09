package github.eddy.bigdata.bilibili.task;

import github.eddy.bigdata.bilibili.common.CategoryMap;
import github.eddy.bigdata.bilibili.task.crawler.ApiSearch;
import github.eddy.bigdata.core.dao.MongodbDao;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static github.eddy.bigdata.core.common.TableEnum.source;
import static github.eddy.common.DateTools.getYYYYMM;

/**
 * 数据抓取
 */
@Slf4j
@UtilityClass
public class CrawlerTask {

    public static void search(int year, int month) {
        String sourceTable = source.table("search", getYYYYMM(year, month));
        MongodbDao.drop(sourceTable);
        log.info("清空元数据");

        Set<Integer> cateIds = CategoryMap.getCategoryIds();

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (Integer cateId : cateIds) {
            cachedThreadPool.execute(() -> {
                ApiSearch apiSearch = new ApiSearch();
                apiSearch.monitorMonthData(year, month, cateId, list -> MongodbDao.insert(sourceTable, list));
                log.info("{}的视频已处理完成", CategoryMap.getCategoryName(cateId));
            });
        }
        log.info("{}-{}视频数据已全部插入到{}", year, month, sourceTable);
    }
}
