import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

public class SearchTest {

  @Test
  public void test() {
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    executorService.execute(() -> {
      System.out.println(Thread.currentThread().getName());
      try {
        Thread.sleep(10 * 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    executorService.shutdown();

    try {
      executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(123);
  }

  @Test
  public void test2() {
    BigDecimal bg = BigDecimal.ONE;
    System.out.println(bg.add(BigDecimal.ONE));
    System.out.println(bg.add(BigDecimal.ONE));
  }
}
