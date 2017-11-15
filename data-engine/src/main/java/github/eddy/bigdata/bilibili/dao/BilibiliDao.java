package github.eddy.bigdata.bilibili.dao;

import static github.eddy.bigdata.bilibili.common.DBTableEnum.sys_category_type;

import com.google.common.collect.ImmutableMap;
import github.eddy.bigdata.bilibili.common.CategoryMap;
import github.eddy.bigdata.core.mongo.MongoDao;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * @author edliao
 */
@Slf4j
@UtilityClass
public class BilibiliDao {

  /**
   * init
   */
  public static void main(String[] a) {
    MongoDao dao = new MongoDao("bilibili");

    //加载视频类型
    dao.drop(sys_category_type.name());
    CategoryMap.getCategoryNames()
        .forEach((integer, s) ->
            dao.insert(sys_category_type.name(),
                ImmutableMap.builder()
                    .put("_id", integer)
                    .put("cateName", s)
                    .build()
            ));
  }
}
