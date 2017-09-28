package github.eddy.bigdata.bilibili.task;

import github.eddy.bigdata.bilibili.common.CategoryMap;
import github.eddy.bigdata.bilibili.task.crawler.SearchRequest;
import github.eddy.bigdata.core.dao.MongodbDao;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static github.eddy.bigdata.bilibili.common.CategoryMap.getCategoryName;
import static github.eddy.bigdata.bilibili.common.DBTableEnum.source_search;
import static github.eddy.common.DateTools.getYYYYMM;

@Slf4j
public class BilibiliCrawler {
    /**
     * 多线程抓取search接口数据
     */
    public void search(int year, int month) {
        String out = source_search.table(getYYYYMM(year, month));
        MongodbDao.drop(out);

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        for (Integer cateId : CategoryMap.getCategoryIds()) {
            fixedThreadPool.execute(() -> {
                SearchRequest request = new SearchRequest(cateId, year, month);
                while (request.hasNext()) {
                    try {
                        MongodbDao.insert(out, request.next().getResult());
                    } catch (Exception e) {
                        log.error("获取数据失败:" + request.getLongURL(), e);
                    }
                }
                log.info("{}的视频已处理完成", getCategoryName(cateId));
            });
        }
        fixedThreadPool.shutdown();

        try {
            fixedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
            log.info("{}-{}视频数据已全部插入到{}", year, month, out);
        } catch (InterruptedException e) {
            log.error("", e);
        }
    }
    //https://api.bilibili.com/x/v2/reply?jsonp=jsonp&pn=2&type=1&oid=14237108&sort=0
    //https://api.bilibili.com/archive_rank/getarchiverankbypartion?&type=jsonp&tid=19&pn=2
}
