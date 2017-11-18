package github.eddy.common;

import java.util.function.BiPredicate;

/**
 * @author edliao 用于比较对象的扩展工具
 */
public class CompareTool {

  /**
   * 根据条件比较对象 ,true返回第一个 ,false返回第二个
   */
  public static <T> T choose(BiPredicate<T, T> predicate, T o1, T o2) {
    return predicate.test(o1, o2) ? o1 : o2;
  }

}
