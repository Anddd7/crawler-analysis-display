package github.eddy.common;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author edliao on 2017/7/7.
 * @description JDBC工具类
 */
public class JDBCTool {

  private JDBCTool() {

  }

  /**
   * 获取Table的字段
   */
  public static List<String> getColumnNames(ResultSetMetaData metaData) throws SQLException {
    List<String> columns = new ArrayList<>();
    for (int i = 0; i < metaData.getColumnCount(); i++) {
      columns.add(metaData.getColumnName(i));
    }
    return columns;
  }


}
