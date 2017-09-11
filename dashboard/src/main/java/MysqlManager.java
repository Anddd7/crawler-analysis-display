package github.eddy.bigdata.core.configuration;

import lombok.Getter;
import lombok.experimental.UtilityClass;
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

@UtilityClass
public class MysqlManager {

  private static final ConnectionSource source;
  private static final DBStyle dbStyle = new MySqlStyle();
  private static final SQLLoader loader = new ClasspathLoader("/sql");
  private static final UnderlinedNameConversion nameConversion = new UnderlinedNameConversion();
  @Getter
  private static final SQLManager sqlManager;

  static {
    source = ConnectionSourceHelper.getSimple("com.mysql.jdbc.Driver",
        "jdbc:mysql://localhost:3306/bilibili?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
        "root", "root");

    sqlManager = new SQLManager(dbStyle, loader, source, nameConversion,
        new Interceptor[]{new DebugInterceptor()});
  }
}
