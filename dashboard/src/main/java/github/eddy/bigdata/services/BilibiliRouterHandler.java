package github.eddy.bigdata.services;

import static io.vertx.ext.sync.Sync.awaitResult;

import co.paralleluniverse.fibers.Suspendable;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.sync.Sync;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * @author edliao
 * @since 11/16/2017 TODO
 */
@Slf4j
public class BilibiliRouterHandler extends AbstractRouterHandler {

  public BilibiliRouterHandler(Vertx vertx, Router router) {
    super(vertx, router);
  }

  @Override
  void initRouterHandler(Vertx vertx) {
    this.mongo = MongoClient.createShared(vertx,
        new JsonObject()
            .put("connection_string", "mongodb://localhost:27017")
            .put("db_name", "bilibili"));
    this.redis = RedisClient.create(vertx, new RedisOptions().setHost("127.0.0.1"));
  }

  @Override
  void mappingRouterHandler(Router router) {
    router.get("/api/mongodb/list").handler(Sync.fiberHandler(this::listMongoCollections));
    router.get("/api/mongodb/get/:collectionName").handler(this::getMongoCollection);
  }

  private MongoClient mongo;
  private RedisClient redis;

  @Suspendable
  private void listMongoCollections(RoutingContext ctx) {
    mongo.getCollections(event -> {
          Map<String, Long> map = new HashMap<>();
          for (String s : event.result()) {
            Long count = awaitResult(h -> mongo.count(s, new JsonObject(), h));
            map.put(s, count);
          }
          responseAsJson(ctx, map);
        }
    );
    log.info("使用sync标记handler后方法仍旧异步 ,使用awaitResult开启同步等待");
  }

  private void getMongoCollection(RoutingContext ctx) {
    //capturing param in url
    String collectionName = ctx.request().getParam("collectionName");

    //find in mongo
    mongo.find(collectionName, new JsonObject(), event -> {
      if (event.failed()) {
        responseFailed(ctx, event);
      } else {
        responseAsJson(ctx, event.result());
      }
    });
  }

  public void getRedis() {
    redis.set("key", "value", r -> {
      if (r.succeeded()) {
        System.out.println("key stored");
        redis.get("key", s -> {
          System.out.println("Retrieved value: " + s.result());
        });
      } else {
        System.out.println("Connection or Operation Failed " + r.cause());
      }
    });
  }
}
