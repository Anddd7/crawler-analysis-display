package github.eddy.bigdata.core.dao;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static github.eddy.bigdata.core.configuration.MongoManager.getCollection;

@Slf4j
@UtilityClass
public class MongodbDao {

    public static void drop(String collectionName) {
        getCollection(collectionName).drop();
        log.info("Mongodb:{}已删除", collectionName);
    }

    public static void insert(String collectionName, Map data) {
        getCollection(collectionName).insertOne(new Document(data));
    }

    public static void insert(String collectionName, List<Map> data) {
        List<Document> documents = new ArrayList<>();
        data.forEach(map -> documents.add(new Document(map)));
        getCollection(collectionName).insertMany(documents);
    }

}
