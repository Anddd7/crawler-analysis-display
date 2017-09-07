import github.eddy.bigdata.bilibili.crawler.api.messages.SearchRequest;
import github.eddy.bigdata.bilibili.crawler.api.messages.SearchResponse;
import github.eddy.bigdata.bilibili.model.SearchSourceSample;
import github.eddy.bigdata.core.CheckException;
import java.util.List;
import org.beetl.sql.core.ClasspathLoader;
import org.beetl.sql.core.ConnectionSource;
import org.beetl.sql.core.ConnectionSourceHelper;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.SQLLoader;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.gen.GenConfig;
import org.beetl.sql.ext.gen.MapperCodeGen;
import org.junit.Test;

public class BeeltSQLTest {

  @Test
  public void test() throws Exception {
    ConnectionSource source = ConnectionSourceHelper.getSimple("com.mysql.jdbc.Driver",
        "jdbc:mysql://127.0.0.1:3306/bilibili?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
        "root", "root");
    DBStyle mysql = new MySqlStyle();
    SQLLoader loader = new ClasspathLoader("/sql");
    UnderlinedNameConversion nc = new UnderlinedNameConversion();
    SQLManager sqlManager = new SQLManager(mysql, loader, source, nc,
        new Interceptor[]{new DebugInterceptor()});

    GenConfig config = new GenConfig();
    config.preferBigDecimal(true);
    MapperCodeGen mapper = new MapperCodeGen("github.eddy.bigdata.bilibili.dao");
    config.codeGens.add(mapper);

    sqlManager.genSQLFile("search_source_sample");
    sqlManager.genPojoCode("search_source_sample", "github.eddy.bigdata.bilibili.model", config);
  }

  @Test
  public void test1() throws CheckException {
    ConnectionSource source = ConnectionSourceHelper.getSimple("com.mysql.jdbc.Driver",
        "jdbc:mysql://127.0.0.1:3306/bilibili?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
        "root", "root");
    DBStyle mysql = new MySqlStyle();
    SQLLoader loader = new ClasspathLoader("/sql");
    UnderlinedNameConversion nc = new UnderlinedNameConversion();
    SQLManager sqlManager = new SQLManager(mysql, loader, source, nc,
        new Interceptor[]{new DebugInterceptor()});

    SearchRequest request = new SearchRequest(19, "20170801", "20170831");
    SearchResponse response = request.get(1);
    List<SearchSourceSample> result = response.getResult();
    sqlManager.insert(result.get(0));
  }

}
