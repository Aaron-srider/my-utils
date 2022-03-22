package fit.wenchao.utils.proxy;

import fit.wenchao.utils.reflect.ReflectUtils;

import java.lang.reflect.*;


public class ObjectWrapperUtils {
    public static < E, I extends E,  V extends InvocationHandler & ProxySetter<E>>
    E getInstance(
            Class<I> enhanceImplType,
            Object underlying,
            Class<V> invocationHandlerType) throws Exception {

        //构造enhance对象
        E enhance = null;
        if (ReflectUtils.hasPublicDefaultConstructor(enhanceImplType)) {
            enhance = enhanceImplType.newInstance();
            Field firstFieldAnnotatedBy = ReflectUtils.findFirstFieldAnnotatedBy(Underlying.class,
                    enhanceImplType);
            if(firstFieldAnnotatedBy!=null) {
                firstFieldAnnotatedBy.setAccessible(true);
                firstFieldAnnotatedBy.set(enhance, underlying);
            } else{
                throw new RuntimeException("the class:" + enhanceImplType + " must has a field annotated by:"+Underlying.class.getName()+".");
            }
        } else {
            throw new RuntimeException("the class:" + enhanceImplType + " must has an public default constructor");
        }

        Class<E>  enhanceInterface = ReflectUtils.findFirstInterfaceAnnotatedBy(EnhanceInterface.class,
                enhanceImplType );

        if(enhanceInterface==null) {
            throw new NoClassFoundException("class:{} should implements at least one interface annotated" +
                    " by @EnhanceInterface");
        }

        //构造InvocationHandler
        V invocationHandler = invocationHandlerType.newInstance();
        invocationHandler.setProxy(enhance);
        return (E) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                new Class[]{enhanceInterface},
                invocationHandler);
    }


}

