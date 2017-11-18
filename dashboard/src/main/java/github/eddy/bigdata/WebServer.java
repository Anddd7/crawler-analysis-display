package github.eddy.bigdata;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import java.io.IOException;

/**
 * @author edliao Vertx 搭建的Http服务器
 */
public class WebServer {

  private final Vertx vertx = Vertx.vertx();

  public static void main(String[] args) throws IOException {
    WebServer webServer = new WebServer();
    webServer.start();
  }

  public void start() throws IOException {
    Router router = Router.router(vertx);

    bilibiliRoute(router);

    vertx.createHttpServer().requestHandler(router::accept).listen(9999);
  }

  private final BilibiliRouterHandler bilibiliHandler = new BilibiliRouterHandler(vertx);

  private void bilibiliRoute(Router router) {
    router.get("/api/mongodb/:collectionName").handler(bilibiliHandler::getMongoCollection);
  }
}
