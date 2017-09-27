package github.eddy.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Map;

public class ReflectTool {

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


    public static <T> T convertMapToPOJO(Map map, Class<T> pojoClass) {
        T object = null;
        try {
            object = pojoClass.newInstance();
            Field[] fields = pojoClass.getDeclaredFields();
            for (Field field : fields) {
                String sourceName = field.getName();
                Convert convert = field.getAnnotation(Convert.class);
                if (convert != null) {
                    sourceName = convert.value();
                }
                if (map.containsKey(sourceName)) {
                    Object value = map.get(sourceName);
                    if (value instanceof Map) {
                        value = convertMapToPOJO((Map) value, field.getType());
                    }
                    setFieldOfObject(field, object, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
