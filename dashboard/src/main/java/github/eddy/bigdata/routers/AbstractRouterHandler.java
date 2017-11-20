package github.eddy.bigdata.routers;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author edliao
 * @since 11/20/2017 抽象路由配置器
 */
public abstract class AbstractRouterHandler {

  private Vertx vertx;
  private Router router;

  protected AbstractRouterHandler(Vertx vertx, Router router) {
    this.vertx = vertx;
    this.router = router;
  }

  public AbstractRouterHandler buildRouterHandler() {
    initRouterHandler(vertx);
    mappingRouterHandler(router);
    return this;
  }

  /**
   * 初始化当前router handler ,比如加载mongo/redis等支持
   */
  abstract void initRouterHandler(Vertx vertx);

  /**
   * 配置路径mapping
   */
  abstract void mappingRouterHandler(Router router);

  protected void responseFailed(RoutingContext ctx, AsyncResult result) {
    ctx.fail(result.cause());
  }

  protected void responseAsJson(RoutingContext ctx, List list) {
    responseAsJson(ctx, new JsonArray(list).encode());
  }

  protected void responseAsJson(RoutingContext ctx, Object obj) {
    responseAsJson(ctx, JsonObject.mapFrom(obj).encode());
  }

  protected void responseAsJson(RoutingContext ctx, String text) {
    ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json");
    ctx.response().end(text);
  }

  protected <T> void executeBlocking(Supplier<T> supplier, Handler<AsyncResult<T>> resultHandler) {
    vertx.executeBlocking(future -> future.complete(supplier.get()), resultHandler);
  }
}
