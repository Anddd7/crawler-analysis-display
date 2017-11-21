package github.eddy.bigdata.rxjava;

import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.observables.AsyncOnSubscribe;
import rx.observables.SyncOnSubscribe;

/**
 * @author edliao
 * @since 11/21/2017 RxJava Samples
 *
 * RxJava 1.x 的基本用法 ,与vertx集成写出完全响应式的代码
 */
public class RxJavaTest {

  /**
   * 被观察者(发布者) 发布一系列数据 ,并注册一个 观察者(订阅者) 去监听他发送的数据
   */
  @Test
  public void test() {
    //已有数据通过just/from转为发布者 , 使用Action1[lambda]创建简单订阅者
    Observable.just("Hello").subscribe(s -> System.out.println(s));
    Observable.from(new String[]{"Welcome", "to", "RxJava"})
        .subscribe(s -> System.out.println(s + " "));
  }

  /**
   * 使用create和new创建完整的 observable和observer 对象
   */
  @Test
  public void test1() {
    //使用create创建被观察者
    Observable<Integer> observable = Observable.create(subscriber -> {
      for (int i = 0; i < 10; i++) {
        if (!subscriber.isUnsubscribed()) {
          subscriber.onNext(i);
        }
      }
      subscriber.onCompleted();
    });

    //new 创建观察者
    Observer<Integer> observer = new Observer<Integer>() {
      @Override
      public void onCompleted() {
        System.out.println("Has completed");
      }

      @Override
      public void onError(Throwable e) {
        e.printStackTrace();
      }

      @Override
      public void onNext(Integer o) {
        System.out.println(o);
      }
    };

    //Subscriber是实现了observer的对象 ,提供了一些扩展方法 onStart/unsubscribe
    Subscriber<Integer> subscriber = new Subscriber<Integer>() {
      @Override
      public void onCompleted() {
        System.out.println("Has completed");
      }

      @Override
      public void onError(Throwable e) {
        e.printStackTrace();
      }

      @Override
      public void onNext(Integer o) {
        System.out.println(o);
      }
    };

    //订阅
    observable.subscribe(observer);
    observable.subscribe(subscriber);
  }

  /**
   * 使用推荐的SyncOnSubscribe/AsyncOnSubscribe
   */
  @Test
  public void test2() throws InterruptedException {
    //SyncOnSubscribe.createStateless 函数中不能执行多次onNext
    Observable<Integer> syncStateless = Observable
        .create(SyncOnSubscribe.createStateless(observer -> {
          observer.onNext(1);
          observer.onCompleted();
        }));

    //使用stateful ,可以在不同state状态下执行onNext
    Observable<Integer> syncObservable = Observable
        .create(SyncOnSubscribe
            .createStateful(
                () -> 0,
                (integer, observer) -> {
                  if (integer < 10) {
                    observer.onNext(integer);
                  } else {
                    observer.onCompleted();
                  }
                  return integer + 1;
                }
            )
        );

    //异步调用是可以在内部去调用其他被观察者 , 无状态时同样只能调用一次onNext
    Observable<Integer> asyncStateless = Observable
        .create(AsyncOnSubscribe.createStateless((aLong, observableObserver) -> {
          observableObserver.onNext(Observable.create(subscriber -> {
            try {
              Thread.sleep(2000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            for (int i = 0; i < 10; i++) {
              if (!subscriber.isUnsubscribed()) {
                subscriber.onNext(i);
              }
            }
            subscriber.onCompleted();
          }));
          observableObserver.onCompleted();
        }));

    //new 创建观察者
    Observer<Integer> observer = new Observer<Integer>() {
      @Override
      public void onCompleted() {
        System.out.println("Has completed");
      }

      @Override
      public void onError(Throwable e) {
        e.printStackTrace();
      }

      @Override
      public void onNext(Integer o) {
        System.out.println(o);
      }
    };

    syncStateless.subscribe(observer);
    System.out.println("syncStateless");
    syncObservable.subscribe(observer);
    System.out.println("syncObservable");
    asyncStateless.subscribe(observer);
    System.out.println("asyncStateless");
  }

  /*@Test
  public void test3() {
    Observable<HttpResponse1> request1 = Observable  .create(new AsyncOnSubscribe<Integer, HttpResponse1>() {
          @Override
          protected Integer generateState() {
            return 0;
          }

          @Override
          protected Integer next(Integer state, long requested,
              Observer<Observable<? extends HttpResponse1>> observer) {
            final Observable<HttpResponse1> asyncObservable = Observable .create(new Observable.OnSubscribe<HttpResponse1>() {
                  @Override
                  public void call(final Subscriber<? super HttpResponse1> subscriber) {
                    //启动第一个异步请求
                    httpService.doRequest("http://...", new HttpRequest1(),
                        new HttpListener<HttpRequest1, HttpResponse1>() {
                          @Override
                          public void onResult(String apiUrl, HttpRequest1 request,
                              HttpResult<HttpResponse1> result, Object contextData) {
                            //第一个异步请求结束, 向asyncObservable中发送结果
                            if (result.getErrorCode() == HttpResult.SUCCESS) {
                              subscriber.onNext(result.getResponse());
                              subscriber.onCompleted();
                            } else {
                              subscriber.onError(new Exception("request1 failed"));
                            }
                          }
                        },
                        null);
                  }
                });
            observer.onNext(asyncObservable);
            observer.onCompleted();
            return 1;
          }
        });
  }*/
}
