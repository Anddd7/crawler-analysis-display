package github.eddy.bigdata;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

import java.io.IOException;

/**
 * Vertx 搭建的Http服务器
 */
public class WebServer {
    private final Vertx vertx = Vertx.vertx();
    private final MongoClient mongo = MongoClient.createShared(vertx,
            new JsonObject().put("connection_string", "mongodb://localhost:27017").put("db_name", "bilibili"));


    public static void main(String[] args) throws IOException {
        WebServer webServer = new WebServer();
        webServer.start();
    }

    public void start() throws IOException {
        Router router = Router.router(vertx);

        staticRoute(router);
        mongoRoute(router);

        vertx.createHttpServer().requestHandler(router::accept).listen(9999);
    }

    private void staticRoute(Router router) {
        //reset vertx root path
        String path = System.getProperty("user.dir");
        System.setProperty("vertx.cwd", path);

        //redirect
        router.route("/static/*").handler(StaticHandler.create("webapp").setCachingEnabled(false));
    }

    private void restRoute(Router router) {

    }

    private void mongoRoute(Router router) {
        router.get("/api/mongodb/:collectionName").handler(ctx -> {
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
        });
    }
}
