package fit.wenchao.utils.web.req;


import fit.wenchao.utils.proxy.ObjectWrapperBase;
import fit.wenchao.utils.proxy.Underlying;

import javax.servlet.http.HttpServletRequest;

import static fit.wenchao.utils.basic.BasicUtils.gloop;

public class HttpServletReqInvocationHandler<E> extends ObjectWrapperBase<E> {

    //必须有，泛型构造
    public HttpServletReqInvocationHandler() {
        super();
    }

    @Override
    public void setEnhanceAndUnderlying(ObjectWrapperBase.Registry registry) {
        registry.registerEnhanceType(HttpServletReqEnhance.class);
        registry.registerUnderlyingType(HttpServletRequest.class);
    }
}