package github.eddy.bigdata.core.configuration;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.experimental.UtilityClass;
import org.apache.hadoop.conf.Configuration;
import org.bson.Document;

@UtilityClass
public class MongoManager {

  private static final MongoClient mongoClient;
  private static final MongoDatabase mongoDB;

  static {
    mongoClient = new MongoClient("localhost", 27017);
    mongoDB = mongoClient.getDatabase("bilibili");
  }

  public static MongoCollection<Document> getCollection(String collectionName) {
    return mongoDB.getCollection(collectionName);
  }

  //----------------------------------------------------------------------------------
  public static Configuration mongoConfig(String input, String output) {
    Configuration mongoConfig = new Configuration();
    mongoConfig.set("mongo.input.uri", "mongodb://localhost/bilibili." + input);
    mongoConfig.set("mongo.output.uri", "mongodb://localhost/bilibili." + output);
    return mongoConfig;
  }
}
