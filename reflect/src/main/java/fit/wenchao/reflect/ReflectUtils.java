package fit.wenchao.reflect;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectUtils {

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
//
//    public static <T> boolean hasPublicDefaultConstructor(Class<T> clazz) {
//        Constructor<?>[] constructors = clazz.getConstructors();
//        boolean found = false;
//        for (Constructor<?> constructor : constructors) {
//            int parameterCount = constructor.getParameterCount();
//            if (parameterCount == 0 && isPublic(constructor.getModifiers())) {
//                found = true;
//                break;
//            }
//        }
//        return found;
//    }
//
//    public static <T> boolean hasPublicConstructor(Class<T> clazz, Class<?>[] argTypes) {
//        try {
//            Constructor<T> constructor = clazz.getConstructor(argTypes);
//            if (isPublic(constructor.getModifiers())) {
//                return true;
//            }
//            throw new NoSuchMethodException();
//        }
//        catch (NoSuchMethodException e) {
//            System.out.println("constructor not found or is not public.");
//            return false;
//        }
//    }
//
//
//    public static <A extends Annotation, T> Field findFirstFieldAnnotatedBy(Class<A> annoType, Class<T> clazz) {
//        AtomicReference<Object> o = null;
//        Field[] declaredFields = clazz.getDeclaredFields();
//
//        Field field = null;
//        for (Field declaredField : declaredFields) {
//            A anno = declaredField.getAnnotation(annoType);
//            if (anno != null) {
//                field = declaredField;
//                break;
//            }
//        }
//
//        return field;
//    }
//
//
//    public static <A extends Annotation, T> Class findFirstInterfaceAnnotatedBy
//            (Class<A> annoType, Class<T> clazz) {
//
//        Class<?>[] interfaces = clazz.getInterfaces();
//
//        Class targetInterface = null;
//
//        boolean found = false;
//        for (Class<?> anInterface : interfaces) {
//            A annotation = anInterface.getAnnotation(annoType);
//
//            if (annotation != null) {
//                targetInterface = anInterface;
//            }
//            found = true;
//            break;
//        }
//
//
//        if (found) {
//            return targetInterface;
//        }
//        else {
//            return findFirstInterfaceAnnotatedBy(annoType, clazz.getSuperclass());
//        }
//    }
//
}
//
//@Retention(RetentionPolicy.RUNTIME)
//@interface TestAnno {
//
//}
//
//@TestAnno
//interface Test {
//
//}
//
////@TestAnno
//class A implements Test {
//
//}
//
//@TestAnno
//class AA extends A {
//    public void func() {
//
//    }
//}
//
//class AAA extends AA {
//    public void func() {
//
//    }
//}
