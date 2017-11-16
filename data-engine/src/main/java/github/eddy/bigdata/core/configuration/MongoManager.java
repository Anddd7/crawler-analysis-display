package github.eddy.bigdata.core.configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoDatabase;
import github.eddy.bigdata.core.configuration.MongoCodecProvider.InstantCodec;
import github.eddy.bigdata.core.configuration.MongoCodecProvider.TaskStatusEnumCodec;
import lombok.experimental.UtilityClass;
import org.apache.hadoop.conf.Configuration;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * @author edliao mongoDB配置
 */
@UtilityClass
public class MongoManager {

  private final MongoClient mongoClient;

  static {
    CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
        CodecRegistries.fromCodecs(new InstantCodec(), new TaskStatusEnumCodec()),
        CodecRegistries.fromProviders(new MongoCodecProvider()),
        MongoClient.getDefaultCodecRegistry()
    );

    MongoClientOptions.Builder build = new MongoClientOptions.Builder();
    build.codecRegistry(codecRegistry);
    //与目标数据库能够建立的最大connection数量为10
    build.connectionsPerHost(10);
    //如果当前所有的connection都在使用中，则每个connection上可以有10个线程排队等待
    build.threadsAllowedToBlockForConnectionMultiplier(10);
            /*
             * 一个线程访问数据库的时候，在成功获取到一个可用数据库连接之前的最长等待时间为2分钟
             * 这里比较危险，如果超过maxWaitTime都没有获取到这个连接的话，该线程就会抛出Exception
             * 故这里设置的maxWaitTime应该足够大，以免由于排队线程过多造成的数据库访问失败
             */
    build.maxWaitTime(1000 * 60 * 2);
    //与数据库建立连接的timeout设置为1分钟
    build.connectTimeout(1000 * 60);

    MongoClientOptions options = build.build();

    mongoClient = new MongoClient("localhost", options);
  }

  public MongoDatabase getDatabase(String dbName) {
    return mongoClient.getDatabase(dbName);
  }


  /**
   * 为Hadoop配置mongo的url
   *
   * @param input mongo->hadoop的表
   * @param output hadoop->mongo的表
   */
  public Configuration mongoConfig(String collectionName, String input, String output) {
    Configuration mongoConfig = new Configuration();
    mongoConfig.set("mongo.input.uri", "mongodb://localhost/" + collectionName + "." + input);
    mongoConfig.set("mongo.output.uri", "mongodb://localhost/" + collectionName + "." + output);
    return mongoConfig;
  }
}
