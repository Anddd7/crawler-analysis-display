import static github.eddy.bigdata.core.common.TableEnum.source;
import static github.eddy.common.DateTools.getYYYYMM;
import static java.util.stream.Collectors.toList;

import github.eddy.bigdata.bilibili.task.AnalysisTask;
import github.eddy.bigdata.bilibili.task.CrawlerTask;
import github.eddy.bigdata.bilibili.task.analysis.TagSplit.SpliterMapper;
import github.eddy.bigdata.bilibili.task.crawler.messages.SearchRequest;
import github.eddy.bigdata.bilibili.task.crawler.messages.SearchResponse;
import github.eddy.bigdata.core.dao.MongodbDao;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.Test;

public class SearchTest {

  @Test
  public void test() throws IOException {
    String sourceTable = source.getTableName("search", getYYYYMM(2017, 9));

    SearchRequest request = new SearchRequest(19, "20170901", "20170930");
    while (request.hasNext()) {
      try {
        SearchResponse response = request.next();
        List<Map> data = response.getResult();
        MongodbDao.insert(sourceTable, data);
      } catch (IOException e) {
        e.printStackTrace();
        System.out.println("获取数据失败:" + request.getLongURL());
      }
    }
  }

  @Test
  public void test1() {
    System.out.println(new Date(1504012510 * 1000L));
    System.out.println(new Date(1504032835 * 1000L));
  }

  @Test
  public void test2() throws InterruptedException, IOException, ClassNotFoundException {
    /**
     * 抓取数据 : 抓取指定年月的数据
     */
    //CrawlerTask.search(2017, 8);
    /**
     * hadoop任务处理数据
     */
   //AnalysisTask.tagCount(2017, 9);
    AnalysisTask.categoryData(2017, 9);
  }

  @Test
  public void test3() {
    Type type = SpliterMapper.class.getGenericSuperclass();
    ParameterizedType type1 = (ParameterizedType) type;
    Stream.of(type1.getActualTypeArguments()).map(type2 -> (Class) type2).collect(toList())
        .forEach(aClass -> {
          System.out.println(aClass.getName());
        });
  }
}
