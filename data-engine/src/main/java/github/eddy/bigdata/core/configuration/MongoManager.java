package github.eddy.bigdata.core.configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
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
        MongoClientOptions.Builder build = new MongoClientOptions.Builder();
        build.connectionsPerHost(10);   //与目标数据库能够建立的最大connection数量为50
        build.threadsAllowedToBlockForConnectionMultiplier(10); //如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
            /*
             * 一个线程访问数据库的时候，在成功获取到一个可用数据库连接之前的最长等待时间为2分钟
             * 这里比较危险，如果超过maxWaitTime都没有获取到这个连接的话，该线程就会抛出Exception
             * 故这里设置的maxWaitTime应该足够大，以免由于排队线程过多造成的数据库访问失败
             */
        build.maxWaitTime(1000 * 60 * 2);
        build.connectTimeout(1000 * 60 * 1);    //与数据库建立连接的timeout设置为1分钟

        MongoClientOptions options = build.build();

        mongoClient = new MongoClient("localhost", options);
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
