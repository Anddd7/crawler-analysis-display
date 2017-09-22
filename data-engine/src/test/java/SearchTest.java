import github.eddy.bigdata.bilibili.task.AnalysisApi;
import github.eddy.bigdata.bilibili.task.CrawlerApi;
import github.eddy.bigdata.bilibili.task.crawler.SearchRequest;
import github.eddy.bigdata.bilibili.task.crawler.SearchResponse;
import github.eddy.bigdata.core.dao.MongodbDao;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static github.eddy.bigdata.core.common.TableEnum.source;
import static github.eddy.common.DateTools.getYYYYMM;

public class SearchTest {

    @Test
    public void test() throws IOException {
        String sourceTable = source.table("search", getYYYYMM(2017, 9));

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
        CrawlerApi.search(2017, 7);
    }

    @Test
    public void test3() {
        /**
         * hadoop任务处理数据
         */
        //AnalysisTask.tagCount(2017, 8);
        AnalysisApi.categoryData(2017, 9);
    }

    public static void main(String[] a) {
        CrawlerApi.search(2017, 7);
    }
}
