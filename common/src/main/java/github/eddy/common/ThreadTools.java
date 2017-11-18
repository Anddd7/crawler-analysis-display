package github.eddy.common;

/**
 * @author edliao 线程工具
 */
public class ThreadTools {

  public static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
