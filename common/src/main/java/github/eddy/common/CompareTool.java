package github.eddy.common;

import java.util.function.Supplier;

public class CompareTool {

  /**
   * 仿Optional.orElse ,当 o1==null 时才会调用supplier获取o2
   */
  public static <T> T getOrElse(T o1, Supplier<T> supplier) {
    if (o1 != null) {
      return o1;
    }
    return supplier.get();
  }

  public static <T> T equalOrElse(T o1, T o2, Supplier<T> supplier) {
    if (o1.equals(o2)) {
      return o1;
    }
    return supplier.get();
  }

  public static <T> T notEqualOrElse(T o1, T o2, Supplier<T> supplier) {
    if (!o1.equals(o2)) {
      return o1;
    }
    return supplier.get();
  }
}
