package github.eddy.common;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author edliao on 2017/6/23.
 * @description 数组List工具补充
 *
 * 关于 Arrays.asList vs List.toArray ,操作List和Array谁更快
 * @:1 get(i) vs [i]  : list内部只多了checkRange ,在可用的index内效率几乎没有差别 (单行代码执行非常快)
 * @:2 对于一些方法需要接受数组/List作为参数 ,在测试中把Array -> List速度会比 List->Array 更快
 * @:3 List易用
 *
 * 通用的底层实现用List操作 ,接受Array参数然后转向List
 */
public class CollectionTool {

  private CollectionTool() {
  }

  /**
   * 返回List 数组第一个符合条件的元素 ,没有则返回Optional.empty()
   */
  public static <T> Optional<T> getFirst(T[] objs, Predicate<T> predicate) {
    return getFirst(Arrays.asList(objs), predicate);
  }

  public static <T> Optional<T> getFirst(List<T> objs, Predicate<T> predicate) {
    for (T obj : objs) {
      if (predicate.test(obj)) {
        return Optional.ofNullable(obj);
      }
    }
    return Optional.empty();
  }

  /**
   * 对数组第一个符合条件的元素执行回调
   */
  public static <T> void dealFirst(T[] objs, Predicate<T> predicate, Consumer<T> consumer) {
    getFirst(objs, predicate).ifPresent(consumer);
  }

  public static <T> void dealFirst(List<T> objs, Predicate<T> predicate, Consumer<T> consumer) {
    getFirst(objs, predicate).ifPresent(consumer);
  }

  /**
   * 数组判空
   */
  public static Boolean isEmpty(Object[] objs) {
    return objs == null || objs.length == 0;
  }

  /**
   * 获取Map中第一个符合要求的value值
   */
  public static <K, V> Optional<V> getFirst(Map<K, V> map, Predicate<K> predicate) {
    for (Map.Entry<K, V> entry : map.entrySet()) {
      if (predicate.test(entry.getKey())) {
        return Optional.ofNullable(entry.getValue());
      }
    }
    return Optional.empty();
  }

  /**
   * 处理Map中第一个符合要求的value值
   */
  public static <K, V> void dealFirst(Map<K, V> map, Predicate<K> predicate, Consumer<V> consumer) {
    getFirst(map, predicate).ifPresent(consumer);
  }

  /**
   * 同时操作2个数组
   */
  public static <L, R> void merge(L[] left, R[] right, BiConsumer<L, R> biConsumer) {
    merge(Arrays.asList(left), Arrays.asList(right), biConsumer);
  }

  public static <L, R> void merge(List<L> left, List<R> right, BiConsumer<L, R> biConsumer) {
    int count = left.size() < right.size() ? left.size() : right.size();
    for (int i = 0; i < count; i++) {
      biConsumer.accept(left.get(i), right.get(i));
    }
  }
}
