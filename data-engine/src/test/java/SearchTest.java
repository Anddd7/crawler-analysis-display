import github.eddy.bigdata.bilibili.crawler.api.messages.SearchRequest;
import github.eddy.bigdata.bilibili.crawler.api.messages.SearchResponse;
import java.io.IOException;
import org.junit.Test;

public class SearchTest {

  @Test
  public void test() throws IOException {
    SearchRequest request = new SearchRequest(19, "20170801", "20170831");
    SearchResponse response = request.next();
    System.out.println(response.getJson());
    System.out.println(response.getResult().get(0).getDescription());
  }
}
