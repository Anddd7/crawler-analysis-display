package github.eddy.bigdata.bilibili.task;

import static github.eddy.bigdata.core.common.TableEnum.source;
import static github.eddy.common.DateTools.getDateRangeYYYYMMDD;
import static github.eddy.common.DateTools.getYYYYMM;

import github.eddy.bigdata.bilibili.common.CategoryMap;
import github.eddy.bigdata.bilibili.task.crawler.messages.SearchRequest;
import github.eddy.bigdata.bilibili.task.crawler.messages.SearchResponse;
import github.eddy.bigdata.core.dao.MongodbDao;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据抓取
 */
@Slf4j
@UtilityClass
public class CrawlerTask {

  public static void search(int year, int month) {
    String sourceTable = source.getTableName("search", getYYYYMM(year, month));
    MongodbDao.drop(sourceTable);
    log.info("清空元数据");

    String[] dateRange = getDateRangeYYYYMMDD(year, month);
    CategoryMap.getCategoryIds().forEach(cateId -> {
      SearchRequest request = new SearchRequest(cateId, dateRange[0], dateRange[1]);
      while (request.hasNext()) {
        try {
          SearchResponse response = request.next();
          List<Map> data = response.getResult();
          MongodbDao.insert(sourceTable, data);
        } catch (IOException e) {
          log.error("获取数据失败:" + request.getLongURL(), e);
        }
      }
      log.info("{}的视频已处理完成", CategoryMap.getCategoryName(cateId));
    });
    log.info("{}-{}视频数据已全部插入到{}", dateRange[0], dateRange[1], sourceTable);
  }

}
