package github.eddy.bigdata.core.dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.UpdateResult;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static github.eddy.bigdata.core.configuration.MongoManager.getCollection;

/**
 * @author edliao
 */
@Slf4j
@UtilityClass
public class MongodbDao {

    public static MongoCursor<Document> find(String collectionName, Bson filter) {
        FindIterable<Document> findIterable = getCollection(collectionName).find(filter);
        return findIterable.iterator();
    }

    public static void drop(String collectionName) {
        getCollection(collectionName).drop();
        log.info("Mongodb:{}已删除", collectionName);
    }

    public static void insert(String collectionName, Map data) {
        getCollection(collectionName).insertOne(new Document(data));
    }

    public static void insert(String collectionName, Document data) {
        getCollection(collectionName).insertOne(data);
    }

    public static void insert(String collectionName, List<Map> data) {
        List<Document> documents = new ArrayList<>();
        data.forEach(map -> documents.add(new Document(map)));
        getCollection(collectionName).insertMany(documents);
    }

    public static UpdateResult update(String collectionName, Bson filter, Bson data) {
        return getCollection(collectionName).updateOne(filter, data);
    }
}
