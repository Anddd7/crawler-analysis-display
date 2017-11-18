package github.eddy.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author edliao on 2017/6/23.
 * @description 数组List工具补充 <p> 关于 Arrays.asList vs List.toArray ,操作List和Array谁更快
 * @:1 get(i) vs [i]  : list内部只多了checkRange ,在可用的index内效率几乎没有差别 (单行代码执行非常快)
 * @:2 对于一些方法需要接受数组/List作为参数 ,在测试中把Array -> List速度会比 List->Array 更快
 * @:3 List易用 <p> 通用的底层实现用List操作 ,接受Array参数然后转向List
 */
public class CollectionTool {

  private CollectionTool() {
  }

  /**
   * @see CollectionTool#getFirst(List, Predicate)
   */
  public static <T> Optional<T> getFirst(T[] objs, Predicate<T> predicate) {
    return getFirst(Arrays.asList(objs), predicate);
  }

  /**
   * 返回List 数组第一个符合条件的元素 ,没有则返回Optional.empty()
   *
   * @param objs 待筛选数组
   * @param predicate 条件
   */
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
   *
   * @param objs,predicate {@link CollectionTool#getFirst(Object[], Predicate)}
   * @param consumer 回调函数
   */
  public static <T> void dealFirst(T[] objs, Predicate<T> predicate, Consumer<T> consumer) {
    getFirst(objs, predicate).ifPresent(consumer);
  }

  /**
   * @see CollectionTool#dealFirst(Object[], Predicate, Consumer)
   */
  public static <T> void dealFirst(List<T> objs, Predicate<T> predicate, Consumer<T> consumer) {
    getFirst(objs, predicate).ifPresent(consumer);
  }

  /**
   * 数组判空
   *
   * @param objs 待判断数组
   */
  public static Boolean isEmpty(Object[] objs) {
    return objs == null || objs.length == 0;
  }

  /**
   * 获取Map中第一个符合要求的value值
   *
   * @param map 待筛选Map
   * @param predicate 条件
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
   *
   * @param map,predicate {@link CollectionTool#getFirst(Map, Predicate)}
   * @param consumer 回调函数
   */
  public static <K, V> void dealFirst(Map<K, V> map, Predicate<K> predicate, Consumer<V> consumer) {
    getFirst(map, predicate).ifPresent(consumer);
  }

  /**
   * @see CollectionTool#merge(Object[], Object[], BiConsumer)
   */
  public static <L, R> void merge(L[] left, R[] right, BiConsumer<L, R> biConsumer) {
    merge(Arrays.asList(left), Arrays.asList(right), biConsumer);
  }

  /**
   * 对2个数组相同位置的元素进行归并 ,不会超过较小的数组的长度
   *
   * @param left 左
   * @param right 右
   * @param biConsumer 回调
   */
  public static <L, R> void merge(List<L> left, List<R> right, BiConsumer<L, R> biConsumer) {
    int count = left.size() < right.size() ? left.size() : right.size();
    for (int i = 0; i < count; i++) {
      biConsumer.accept(left.get(i), right.get(i));
    }
  }

  /**
   * 针对非字符串类型的对象进行join
   *
   * @param ch 分隔符
   * @param objs 对象
   */
  public static String join(CharSequence ch, Object... objs) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < objs.length; i++) {
      if (i > 0) {
        sb.append(ch);
      }
      sb.append(objs[i].toString());
    }
    return sb.toString();
  }

  /**
   * @see CollectionTool#formatArray2Map(String[], String[])
   */
  public static Map<String, String> formatArray2Map(String[] titles, String[] datas) {
    return formatArray2Map(Arrays.asList(titles), Arrays.asList(datas));
  }

  /**
   * 把数组变成Map
   *
   * @param titles 字段名
   * @param datas 数据值
   */
  public static Map<String, String> formatArray2Map(List<String> titles, List<String> datas) {
    Map<String, String> map = new HashMap<>();
    for (int i = 0; i < (titles.size() < datas.size() ? titles.size() : datas.size()); i++) {
      map.put(titles.get(i), datas.get(i));
    }
    return map;
  }

  /**
   * 功能同 {@link java.util.stream.Stream#anyMatch(Predicate)} ,stream是函数式调用 ,某些情况下直接返回true false会更方便
   *
   * @param objs 待选项
   * @param predicate 条件
   */
  public static <T> Boolean anyMatch(T[] objs, Predicate<T> predicate) {
    for (T obj : objs) {
      if (predicate.test(obj)) {
        return true;
      }
    }
    return false;
  }

  /**
   * MapBuilder
   */
  public static class MapBuilder {

    Map map;

    public MapBuilder() {
      this.map = new HashMap();
    }

    public MapBuilder(Map map) {
      this.map = map;
    }

    public static MapBuilder of(Map map) {
      return new MapBuilder(map);
    }

    public MapBuilder put(Object key, Object val) {
      map.put(key, val);
      return this;
    }

    public MapBuilder put(Object key, Consumer<MapBuilder> consumer) {
      MapBuilder subBuilder = new MapBuilder();
      consumer.accept(subBuilder);
      return put(key, subBuilder.build());
    }

    public Map build() {
      return map;
    }
  }
}
