package github.eddy.bigdata;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;

/**
 * @author edliao
 * @since 11/16/2017 TODO
 */
public class BilibiliRouterHandler {

  private MongoClient mongo;
  private Vertx vertx;

  public BilibiliRouterHandler(Vertx vertx) {
    this.vertx = vertx;
    this.mongo = MongoClient.createShared(vertx,
        new JsonObject()
            .put("connection_string", "mongodb://localhost:27017")
            .put("db_name", "bilibili"));
  }


  public void getMongoCollection(RoutingContext ctx) {
    //capturing param in url
    String collectionName = ctx.request().getParam("collectionName");

    //find in mongo
    mongo.find(collectionName, new JsonObject(), lookup -> {
      if (lookup.failed()) {
        ctx.fail(lookup.cause());
        return;
      }

      final JsonArray json = new JsonArray();
      for (JsonObject o : lookup.result()) {
        json.add(o);
      }

      ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json");
      ctx.response().end(json.encode());
    });
  }
}
