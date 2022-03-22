package fit.wenchao.utils.reflect;

import fit.wenchao.utils.string.StrUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.*;
import java.util.concurrent.atomic.AtomicReference;

import static fit.wenchao.utils.basic.BasicUtils.forArr;
import static fit.wenchao.utils.basic.BasicUtils.gloop;
import static java.lang.reflect.Modifier.isPublic;
import static java.util.Arrays.asList;

public class ReflectUtils {
    public static <T> T newInstance(Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            StrUtils.outf("can not create instance of class:{} throw default constructor, please " +
                    "check if an public default constructor exists.", clazz.getName());
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T newInstance(Class<T> clazz,
                                    Class<?>[] parameterTypes, Object[] initargs) {
        Constructor<T> constructor = null;
        try {
            constructor = clazz.getConstructor(parameterTypes);
            T t = constructor.newInstance(initargs);
            return t;
        } catch (InvocationTargetException e) {

            StrUtils.outf("constructor:{} throw an exception:{}", asList(parameterTypes),
                    e.getTargetException());
        } catch (NoSuchMethodException e) {
            StrUtils.outf("constructor:{} not found.", asList(parameterTypes));
        } catch (InstantiationException e) {
            StrUtils.outf("abstract class:{} can not be instantiated.", clazz.getName());
        } catch (IllegalAccessException e) {
            StrUtils.outf("constructor:{} with modifiers:{} can not be access.", asList(parameterTypes),
                    Modifier.toString(constructor.getModifiers()));
        }
        return null;
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
