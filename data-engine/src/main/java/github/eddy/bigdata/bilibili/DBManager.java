package github.eddy.bigdata.bilibili;

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

public class DBManager {

  private static final ConnectionSource source;
  private static final DBStyle dbStyle = new MySqlStyle();
  private static final SQLLoader loader = new ClasspathLoader("/sql");
  private static final UnderlinedNameConversion nameConversion = new UnderlinedNameConversion();
  private static final SQLManager sqlManager;

  static {
    source = ConnectionSourceHelper.getSimple("com.dbStyle.jdbc.Driver",
        "jdbc:dbStyle://localhost:3306/bilibili?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
        "root", "root");

    sqlManager = new SQLManager(dbStyle, loader, source, nameConversion,
        new Interceptor[]{new DebugInterceptor()});
  }

  public static SQLManager getSqlManager() {
    return sqlManager;
  }
}
