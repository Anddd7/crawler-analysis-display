package github.eddy.bigdata;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import github.eddy.bigdata.routers.AbstractRouterHandler;
import github.eddy.bigdata.routers.BilibiliRouterHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * @author edliao Vertx 搭建的Http服务器
 */
@Slf4j
public class WebServer {

  private final Vertx vertx = Vertx.vertx();
  Map<String, AbstractRouterHandler> routerHandlers;

  public static void main(String[] args) throws IOException {
    WebServer webServer = new WebServer();
    webServer.start();
  }

  public void start() {
    Router router = Router.router(vertx);
    try {
      initRouterHandlers(router, BilibiliRouterHandler.class);
    } catch (Exception e) {
      log.error("", e);
      System.exit(0);
    }
    vertx.createHttpServer().requestHandler(router::accept).listen(9999);
  }

  private <T extends AbstractRouterHandler> void initRouterHandlers(Router router,
      Class<T>... classes)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    Builder<String, AbstractRouterHandler> builder = ImmutableMap.builder();

    for (Class<T> clazz : classes) {
      Constructor<T> constructor = clazz.getConstructor(Vertx.class, Router.class);
      log.info("Constructor:{} - {}", constructor.toGenericString(), constructor.hashCode());
      AbstractRouterHandler routerHandler = constructor.newInstance(vertx, router);
      builder.put(clazz.getSimpleName(), routerHandler.buildRouterHandler());
    }

    routerHandlers = builder.build();
  }
}
