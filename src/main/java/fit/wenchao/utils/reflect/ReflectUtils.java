package fit.wenchao.utils.reflect;

import fit.wenchao.utils.string.StrUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static fit.wenchao.utils.basic.BasicUtils.forArr;
import static fit.wenchao.utils.basic.BasicUtils.gloop;
import static java.lang.reflect.Modifier.isPublic;
import static java.util.Arrays.asList;

public class ReflectUtils {

    /**
     * get all fields in specific object, including fields from it's superClass
     * @param object target object fields are belong to
     * @return fields from class of target object
     */
    public static Field[] getAllFields(Object object){
        return getAllFields(object.getClass());
    }

    /**
     * get all fields in specific class, including fields from it's superClass
     * @param clazz target class fields are belong to
     * @return fields from target class
     */
    public static Field[] getAllFields(Class clazz){
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null){
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    /**
     * combination function of Class::getConstructor and Constructor::newInstance to
     *  provide a convenience reflect way to invoke constructor of a class ,cause
     * Class::newInstance can only invoke default constructor of target Class.
     * @param clazz target class to be initialized.
     * @param parameterTypes parameterTypes of target constructor to be invoked
     * @param initargs real args that compatible with {@code parameterTypes}
     * @param <T> type var of target class
     * @return a new instance of target class
     * @throws InvocationTargetException target constructor throw an exception
     * @throws IllegalAccessException target constructor is not accessible due to it's modifier
     * @throws NoSuchMethodException there is no such constructor compatible with
     * parameterTypes provided.
     * @throws InstantiationException target class is an abstract one.
     */
    public static <T> T newInstance(Class<T> clazz,
                                    Class<?>[] parameterTypes,
                                    Object[] initargs) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        Constructor<T> constructor = null;
        try {
            constructor = clazz.getConstructor(parameterTypes);
            return constructor.newInstance(initargs);
        } catch (InvocationTargetException e) {
            StrUtils.outf("constructor:{} throw an exception:{}", asList(parameterTypes),
                    e.getTargetException());
            throw e;
        } catch (NoSuchMethodException e) {
            StrUtils.outf("constructor:{} not found.", asList(parameterTypes));
            throw e;
        } catch (InstantiationException e) {
            StrUtils.outf("abstract class:{} can not be instantiated.", clazz.getName());
            throw e;
        } catch (IllegalAccessException e) {
            StrUtils.outf("constructor:{} with modifiers:{} can not be access.", asList(parameterTypes),
                    Modifier.toString(constructor.getModifiers()));
            throw e;
        }
    }

    public static <T> boolean hasPublicDefaultConstructor(Class<T> clazz) throws Exception {
        boolean found = gloop(forArr(clazz.getConstructors()), (i, e, s, f) -> {
            int parameterCount = e.getParameterCount();
            if (parameterCount == 0 && isPublic(e.getModifiers())) {
                f.set(true);
                s.breakLoop();
            }
        });
        return found;
    }

    public static <T> boolean hasPublicConstructor(Class<T> clazz, Class<?>[] argTypes) {
        try {
            Constructor<T> constructor = clazz.getConstructor(argTypes);
            if (isPublic(constructor.getModifiers())) {
                return true;
            }
            //如果
            throw new NoSuchMethodException();
        } catch (NoSuchMethodException e) {
            StrUtils.outf("constructor:{} not found or is not public.", asList(argTypes));
            return false;
        }
    }


    public static <A extends Annotation, T> Field findFirstFieldAnnotatedBy(Class<A> annoType, Class<T> clazz) throws Exception {

        AtomicReference<Object> o = null;
        Field[] declaredFields = clazz.getDeclaredFields();

        Field[] fields = new Field[1];
        gloop(forArr(declaredFields), (i, e, s) -> {
            A anno = e.getAnnotation(annoType);
            if (anno != null) {
                fields[0] = e;
                s.breakLoop();
            }
        });

        return fields[0];
    }


    public static <A extends Annotation, T> Class findFirstInterfaceAnnotatedBy(Class<A> annoType, Class<T> clazz) throws Exception {

        Class<?>[] interfaces = clazz.getInterfaces();

        Class[] targetInterface = new Class[1];

        boolean found = gloop(forArr(interfaces), (i, e, s, f) -> {
            A annotation = e.getAnnotation(annoType);

            if (annotation != null) {
                targetInterface[0] = e;
            }
            f.set(true);
            s.breakLoop();
            return;
        });

        if (found) {
            return targetInterface[0];
        } else {
            return findFirstInterfaceAnnotatedBy(annoType, clazz.getSuperclass());
        }
    }

    public static void main(String[] args) throws Exception {
        Class firstInterfaceAnnotatedBy = findFirstInterfaceAnnotatedBy(TestAnno.class,
                AAA.class);

        System.out.println(firstInterfaceAnnotatedBy);
        Method[] methods = AAA.class.getMethods();
        gloop(forArr(methods), (i, e, s) -> {
            StrUtils.outf("method name:{}", e.getName());
        });
    }

}

@Retention(RetentionPolicy.RUNTIME)
@interface TestAnno{

}

@TestAnno
interface Test{

}

//@TestAnno
class A implements Test{

}

@TestAnno
class AA extends A{
    public void func(){

    }
}

class AAA extends AA{
    public void func(){

    }
}
