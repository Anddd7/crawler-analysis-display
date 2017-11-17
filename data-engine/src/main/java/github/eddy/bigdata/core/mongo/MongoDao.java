package github.eddy.bigdata.core.mongo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import github.eddy.bigdata.core.configuration.MongoManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * mongodb 的一些基本操作
 *
 * @author edliao
 * @since 11/15/2017
 */
@Slf4j
public abstract class MongoDao {

  /**
   * 内部保留的mongodb链接实例
   */
  @Getter
  private MongoDatabase db;
  @Getter
  private String dbName;

  public MongoDao(String dbName) {
    this.dbName = dbName;
    this.db = MongoManager.getDatabase(dbName);
  }


  public MongoCursor<Document> find(String collectionName, Bson filter) {
    FindIterable<Document> findIterable = db.getCollection(collectionName).find(filter);
    return findIterable.iterator();
  }

  public void drop(String collectionName) {
    db.getCollection(collectionName).drop();
    log.info("Mongodb:{}已删除", collectionName);
  }

  public void insert(String collectionName, Map data) {
    db.getCollection(collectionName).insertOne(new Document(data));
  }

  public void insert(String collectionName, Document data) {
    db.getCollection(collectionName).insertOne(data);
  }

  public void insert(String collectionName, List<Map> data) {
    List<Document> documents = new ArrayList<>();
    data.forEach(map -> documents.add(new Document(map)));
    db.getCollection(collectionName).insertMany(documents);
  }

  public UpdateResult update(String collectionName, Bson filter, Bson data) {
    return db.getCollection(collectionName).updateOne(filter, data);
  }

  /**
   * 查询符合条件的collection names
   */
  public List<String> filterCollectionNames(Predicate<String> predicate) {
    List<String> result = new ArrayList<>();
    db.listCollectionNames().iterator()
        .forEachRemaining(s -> {
          if (predicate.test(s)) {
            result.add(s);
          }
        });
    return result;
  }
}
