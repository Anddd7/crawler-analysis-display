package github.eddy.common;

import static github.eddy.common.CollectionTool.anyMatch;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author edliao 反射工具
 */
public class ReflectTool {

  /**
   * set value
   *
   * @param field 属性
   * @param object 对象
   * @param value 属性值
   */
  public static void setFieldOfObject(Field field, Object object, Object value) {
    try {
      field.setAccessible(true);
      field.set(object, value);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  @Target({ElementType.FIELD, ElementType.TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  public @interface Convert {

    String value() default "";
  }

  /**
   * Map to Pojo ,使用 {@link Convert} 可以改变映射的名称
   *
   * @param map map
   * @param pojoClass pojo类
   */
  public static <T> T convertMap2POJO(Map map, Class<T> pojoClass) {
    T object = null;
    try {
      T pojo = pojoClass.newInstance();
      dealFieldValue(object, new Integer[]{Modifier.STATIC, Modifier.FINAL}, false, (field, o) -> {
        String sourceName = field.getName();
        Convert convert = field.getAnnotation(Convert.class);
        if (convert != null) {
          sourceName = convert.value();
        }
        if (map.containsKey(sourceName)) {
          Object value = map.get(sourceName);
          if (value instanceof Map) {
            value = convertMap2POJO((Map) value, field.getType());
          }
          setFieldOfObject(field, pojo, value);
        }
      });
      object = pojo;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return object;
  }

  /**
   * pojo 2 map
   *
   * @param pojo 对象实例
   * @param ignoreNull 忽略空值
   */
  public static <T> Map convertPOJO2Map(T pojo, Boolean ignoreNull) throws IllegalAccessException {
    Builder mapBuilder = ImmutableMap.<String, Object>builder();
    dealFieldValue(pojo, new Integer[]{Modifier.STATIC, Modifier.FINAL}, ignoreNull,
        (field, o) -> mapBuilder.put(field.getName(), o));
    return mapBuilder.build();
  }

  /**
   * 遍历并回调字段和值
   *
   * @param pojo 对象实例
   * @param ignoreModifiers 忽略指定类型的字段
   * @param ignoreNull 忽略空值
   * @param biConsumer 回调
   */
  public static void dealFieldValue(Object pojo, Integer[] ignoreModifiers, Boolean ignoreNull,
      BiConsumer<Field, Object> biConsumer)
      throws IllegalAccessException {
    for (Field field : pojo.getClass().getDeclaredFields()) {
      Integer mod = field.getModifiers();
      if (anyMatch(ignoreModifiers, mod::equals)) {
        continue;
      }

      field.setAccessible(true);
      Object value = field.get(pojo);
      if (value != null || !ignoreNull) {
        biConsumer.accept(field, value);
      }
    }
  }
}
