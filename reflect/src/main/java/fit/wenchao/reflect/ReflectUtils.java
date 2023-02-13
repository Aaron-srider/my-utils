package fit.wenchao.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectUtils {

    private static Logger log = LoggerFactory.getLogger(ReflectUtils.class);

    /**
     * get all fields in specific object, including fields from it's superClass
     *
     * @param object target object fields are belong to
     * @return fields from class of target object
     */
    public static Field[] getAllFields(Object object) {
        return getAllFields(object.getClass());
    }

    /**
     * get all fields in specific class, including fields from it's superClass
     *
     * @param clazz target class fields are belong to
     * @return fields from target class
     */
    public static Field[] getAllFields(Class clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    public static <T> T getFieldValue(Object targetObj, String fieldName, Class<T> expectedFieldType) {
        Field targetField = null;
        Class<?> objClass = targetObj.getClass();
        try {
            targetField = objClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        Class<?> fieldActualClass = targetField.getType();
        if (!expectedFieldType.isAssignableFrom(fieldActualClass)) {
            log.error("Class :{} can not be cast to :{}", fieldActualClass, expectedFieldType);
            throw new RuntimeException(ft("Class :{} can not be cast to :{}", fieldActualClass, expectedFieldType));
        }
        targetField.setAccessible(true);
        T o = null;
        try {
            o = (T) targetField.get(targetObj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return o;
    }
}