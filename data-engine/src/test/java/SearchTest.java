import github.eddy.bigdata.bilibili.analysis.AnalysisTask;
import github.eddy.bigdata.bilibili.crawler.CrawlerTask;
import github.eddy.bigdata.bilibili.crawler.api.messages.SearchRequest;
import github.eddy.bigdata.bilibili.crawler.api.messages.SearchResponse;
import java.io.IOException;
import java.util.Date;
import org.junit.Test;

public class SearchTest {

  @Test
  public void test() throws IOException {
    SearchRequest request = new SearchRequest(19, "20170801", "20170831");
    SearchResponse response = request.next();
    System.out.println(response.getJson());
    System.out.println(response.getResult().get(0));
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
    CrawlerTask.search(2017, 6);
    /**
     * hadoop任务处理数据
     */
    AnalysisTask.tagCount(2017, 6);
  }
}
