package github.eddy.common;

import java.lang.reflect.Field;

public class ReflectTool {

  public static void setFieldOfObject(Field field, Object object, Object value) {
    try {
      field.setAccessible(true);
      field.set(object, value);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
