package github.eddy.bigdata.verticles;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import github.eddy.bigdata.services.AbstractRouterHandler;
import github.eddy.bigdata.services.BilibiliRouterHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * @author edliao
 * @since 11/21/2017 本应用向外提供http服务的verticle
 */
@Slf4j
public class RestServerVerticle extends AbstractVerticle {

  Map<String, AbstractRouterHandler> routerHandlers;

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    Router router = Router.router(vertx);
    try {
      initRouterHandlers(router, BilibiliRouterHandler.class);
    } catch (Exception e) {
      log.error("", e);
      System.exit(0);
    }
    vertx.createHttpServer().requestHandler(router::accept).listen(9999);
    startFuture.complete();
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
