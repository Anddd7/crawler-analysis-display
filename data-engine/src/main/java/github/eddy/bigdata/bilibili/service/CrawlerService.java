package github.eddy.bigdata.bilibili.service;

import static github.eddy.bigdata.bilibili.common.DBTableEnum.source_search;

import github.eddy.bigdata.bilibili.service.common.TaskParams;
import github.eddy.bigdata.bilibili.service.crawler.SearchRequest;
import github.eddy.bigdata.bilibili.service.crawler.SearchResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author edliao Bilibili数据抓取中心 ,因为数据分析不一定和数据抓取同时进行 ,所以抓取的数据会通过数据库暂存 同时暂存的数据也可以进行一些展示
 */
@Slf4j
@Service
public class CrawlerService extends AbstractService {

  public void search(TaskParams taskParams) {
    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    String time = taskParams.getYYYYMMDD();
    String collectionName = source_search
        .table(taskParams.getCateId().toString(), taskParams.getYYYYMM());

    SearchRequest request = new SearchRequest(taskParams.getCateId(), time, time);
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
  //https://api.bilibili.com/x/v2/reply?jsonp=jsonp&pn=2&type=1&oid=14237108&sort=0
  //https://api.bilibili.com/archive_rank/getarchiverankbypartion?&type=jsonp&tid=19&pn=2
}
