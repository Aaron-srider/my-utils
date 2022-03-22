package fit.wenchao.utils.web.response;


import fit.wenchao.utils.proxy.ObjectWrapperBase;

import javax.servlet.http.HttpServletResponse;

public class HttpServletRespInvocationHandler<E> extends ObjectWrapperBase<E> {

    public HttpServletRespInvocationHandler() {
        super();
    }

    @Override
    public void setEnhanceAndUnderlying(Registry registry) {
        registry.registerEnhanceType(HttpServletRespEnhance.class);
        registry.registerUnderlyingType(HttpServletResponse.class);
    }

}

