import github.eddy.bigdata.core.configuration.MysqlManager;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.ext.gen.GenConfig;
import org.beetl.sql.ext.gen.MapperCodeGen;
import org.junit.Test;

public class BeeltSQLTest {

  @Test
  public void test() throws Exception {
    SQLManager sqlManager = MysqlManager.getSqlManager();

    GenConfig config = new GenConfig();
    config.preferBigDecimal(true);
    MapperCodeGen mapper = new MapperCodeGen("github.eddy.bigdata.bilibili.dao");
    config.codeGens.add(mapper);

    sqlManager.genSQLFile("search_source_sample");
    sqlManager.genPojoCode("search_source_sample", "github.eddy.bigdata.bilibili.model", config);
  }

}
