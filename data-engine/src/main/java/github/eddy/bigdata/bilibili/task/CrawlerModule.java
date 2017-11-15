package github.eddy.bigdata.bilibili.task;

import static github.eddy.bigdata.bilibili.common.DBTableEnum.source_search;
import static github.eddy.common.DateTools.getYYYYMMDD;

import github.eddy.bigdata.bilibili.task.crawler.SearchRequest;
import github.eddy.bigdata.bilibili.task.crawler.SearchResponse;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author edliao Bilibili数据抓取中心 ,因为数据分析不一定和数据抓取同时进行 ,所以抓取的数据会通过数据库暂存 同时暂存的数据也可以进行一些展示
 */
@Slf4j
public class CrawlerModule extends AbstractModule {

  public void search(Integer cateId, Integer year, Integer month, Integer day) {
    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    String time = getYYYYMMDD(year, month, day);
    String collectionName = source_search.table(cateId.toString(), getYYYYMMDD(year, month, day));

    SearchRequest request = new SearchRequest(cateId, time, time);
    while (request.getHasNext()) {
      SearchResponse response = request.next();
      //使用子线程去写入数据库
      singleThreadExecutor.execute(() ->
          response.getResult().forEach(map -> {
            map.put("cateid", response.getCateid());
            dao.insert(collectionName, map);
          })
      );
    }
  }

  @Override
  void router(String taskName, List<Object> params) {

  }

  //https://api.bilibili.com/x/v2/reply?jsonp=jsonp&pn=2&type=1&oid=14237108&sort=0
  //https://api.bilibili.com/archive_rank/getarchiverankbypartion?&type=jsonp&tid=19&pn=2
}
