package fit.wenchao.utils.proxy;

import fit.wenchao.utils.reflect.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static fit.wenchao.utils.basic.BasicUtils.gloop;
import static java.util.Arrays.asList;

public abstract class ObjectWrapperBase<E> implements InvocationHandler, ProxySetter<E> {
    static Map<Class, Set<Method>> classMethodsMap = new ConcurrentHashMap<>();

    private Class underlyingType;
    private Class enhanceType;
    protected E enhance;

    protected boolean additionalMethods(Method method) {
        return classMethodsMap.get(enhanceType).contains(method);
    }

    protected boolean proxyMethods(Method method) {
        return classMethodsMap.get(underlyingType).contains(method);
    }

    public abstract void setEnhanceAndUnderlying(Registry registry);

    protected static class Registry {
        Class enhanceType;
        Class underlyingType;

        public void registerEnhanceType(Class enhanceType) {
            this.enhanceType = enhanceType;
        }

        public void registerUnderlyingType(Class underlyingType) {
            this.underlyingType = underlyingType;
        }
    }


    @Deprecated
    public ObjectWrapperBase(E enhance) {
        this.enhance = enhance;
    }

    Set<Method> enhanceTypeMethods;
    Set<Method> underlyingTypeMethods;

    public ObjectWrapperBase() {
        Registry euRegistry = new Registry();
        setEnhanceAndUnderlying(euRegistry);
        this.enhanceType = euRegistry.enhanceType;
        this.underlyingType = euRegistry.underlyingType;

        if (classMethodsMap.get(enhanceType) != null) {
            enhanceTypeMethods = classMethodsMap.get(enhanceType);
        } else {

            Method[] enhanceTypeMethods = enhanceType.getMethods();

            HashSet<Method> methods = new HashSet<>();
            methods.addAll(asList(enhanceTypeMethods));
            classMethodsMap.put(enhanceType, methods);

            this.enhanceTypeMethods = new HashSet<>();
            this.enhanceTypeMethods.addAll(asList(enhanceTypeMethods));
        }

        if (classMethodsMap.get(underlyingType) != null) {
            underlyingTypeMethods = classMethodsMap.get(underlyingType);
        } else {

            Method[] underlyingTypeMethods = underlyingType.getMethods();

            HashSet<Method> methods = new HashSet<>();
            methods.addAll(asList(underlyingTypeMethods));
            classMethodsMap.put(underlyingType, methods);

            this.underlyingTypeMethods = new HashSet<>();
            this.underlyingTypeMethods.addAll(asList(underlyingTypeMethods));
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (proxyMethods(method)) {
            Field firstFieldAnnotatedBy = ReflectUtils.findFirstFieldAnnotatedBy(Underlying.class,
                    enhance.getClass());
            Object underlying = firstFieldAnnotatedBy.get(enhance);
            return method.invoke(underlying, args);
        } else if (additionalMethods(method)) {
            return method.invoke(enhance, args);
        } else {
            throw new IllegalStateException("method:" + method.getName() + " not found");
        }
    }

    @Override
    public void setProxy(E enhance) {
        this.enhance = enhance;
    }

    //protected static <E, U>void  initMethods(Class<E> enhanceType,
    //                                         Class<U> underlyingType) {
    //    Method[] reqMethods = HttpServletRequest.class.getMethods();
    //    Method[] addMethods = HttpServletReqEnhance.class.getMethods();
    //    httpServletReqMethods.addAll(Arrays.asList(reqMethods));
    //    additionalMethods.addAll(Arrays.asList(addMethods));
    //}
    //
    //protected abstract boolean additionalMethods(Method method);
    //
    //protected abstract boolean proxyMethods(Method method);
}
