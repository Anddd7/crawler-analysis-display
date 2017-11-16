package github.eddy.bigdata.bilibili.dao;

import github.eddy.bigdata.core.mongo.MongoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * bilibili数据库链接
 *
 * @author edliao
 */
@Slf4j
@Service
public class BilibiliDao extends MongoDao {

  public BilibiliDao() {
    super("bilibili");
  }

  /**
   * init
   */
  public static void main(String[] a) {
//    BaseMongoDao dao = new BaseMongoDao("bilibili");
//
//    //加载视频类型
//    dao.drop(sys_category_type.name());
//    CategoryMap.getCategoryNames()
//        .forEach((integer, s) ->
//            dao.insert(sys_category_type.name(),
//                ImmutableMap.builder()
//                    .put("_id", integer)
//                    .put("cateName", s)
//                    .build()
//            ));
  }

}
