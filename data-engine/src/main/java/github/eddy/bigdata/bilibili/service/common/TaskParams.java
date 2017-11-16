package github.eddy.bigdata.bilibili.service.common;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import github.eddy.common.DateTools;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author edliao
 * @since 11/16/2017 执行任务所用的参数封装
 *
 * 使用POJO类代替不定长度参数列表 ,路由的时候可以使用反射直接找到对应方法并执行
 */
@Getter
@Setter
@Accessors(chain = true)
@Slf4j
public class TaskParams {

  Integer cateId;
  Integer year;
  Integer month;
  Integer day;

  Map innerMap = null;

  public String getYYYYMMDD() {
    return DateTools.getYYYYMMDD(year, month, day);
  }

  public String getYYYYMM() {
    return DateTools.getYYYYMM(year, month);
  }

  public Map toMap() {
    if (innerMap != null) {
      return innerMap;
    }
    Builder mapBuilder = ImmutableMap.<String, Object>builder();
    for (Field field : TaskParams.class.getDeclaredFields()) {
      int mod = field.getModifiers();
      //过滤 static 和 final 类型
      if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
        continue;
      }
      field.setAccessible(true);
      try {
        Optional.ofNullable(field.get(this))
            .ifPresent(value -> mapBuilder.put(field.getName(), value));
      } catch (IllegalAccessException e) {
        log.error("", e);
      }
    }
    innerMap = mapBuilder.build();
    return innerMap;
  }
}
