import github.eddy.bigdata.bilibili.crawler.api.messages.SearchRequest;
import github.eddy.bigdata.bilibili.crawler.api.messages.SearchResponse;
import github.eddy.bigdata.core.CheckException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import org.junit.Test;

public class SearchTest {

  @Test
  public void test() throws IOException, CheckException {
    SearchRequest request = new SearchRequest(19, "20170801", "20170831");
    SearchResponse response = request.next();
    System.out.println(response.getJson());
    System.out.println(response.getResult().get(0).getDescription());
  }
@Test
  public void test1(){
  System.out.println(new Date(1504012510*1000L));
  System.out.println(new Date(1504032835*1000L));
}
}
