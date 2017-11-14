package github.eddy.bigdata.bilibili.task;

import github.eddy.bigdata.bilibili.task.crawler.SearchRequest;
import github.eddy.bigdata.bilibili.task.crawler.SearchResponse;
import github.eddy.bigdata.core.dao.BilibiliDao;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static github.eddy.bigdata.bilibili.common.DBTableEnum.SOURCE_SEARCH;
import static github.eddy.common.DateTools.*;

/**
 * @author edliao
 * Bilibili数据抓取中心 ,因为数据分析不一定和数据抓取同时进行 ,所以抓取的数据会通过数据库暂存
 * 同时暂存的数据也可以进行一些展示
 */
@Slf4j
public class BilibiliCrawler {
    /**
     * 按年月日抓取单一类别的视频
     *
     * @param cateId 视频类别{@link github.eddy.bigdata.bilibili.common.CategoryMap}
     * @param year
     * @param month
     * @param day    如果为空 ,表示抓取某月的视频
     */
    public void search(Integer cateId, Integer year, Integer month, Integer day) {
        String timeFrom = getYYYYMMDD(year, month, day);
        String timeTo = timeFrom;
        if (day == null) {
            String[] timeRange = getDateRangeYYYYMMDD(year, month);
            timeFrom = timeRange[0];
            timeTo = timeRange[1];
        }

        SearchRequest request = new SearchRequest(cateId, timeFrom, timeTo);

        String collectionName = SOURCE_SEARCH.table(getYYYYMM(year, month));
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        while (request.getHasNext()) {
            SearchResponse response = request.next();
            //使用子线程去写入数据库
            singleThreadExecutor.execute(() ->
                    response.getResult().forEach(map -> {
                        map.put("cateid", response.getCateid());
                        BilibiliDao.insert(collectionName, map);
                    })
            );
        }
    }

    //https://api.bilibili.com/x/v2/reply?jsonp=jsonp&pn=2&type=1&oid=14237108&sort=0
    //https://api.bilibili.com/archive_rank/getarchiverankbypartion?&type=jsonp&tid=19&pn=2
}
