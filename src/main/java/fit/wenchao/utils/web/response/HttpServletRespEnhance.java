package fit.wenchao.utils.web.response;

import fit.wenchao.utils.proxy.EnhanceInterface;
import fit.wenchao.utils.proxy.Underlying;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnhanceInterface
public interface HttpServletRespEnhance extends HttpServletResponse {
    public void returnJson(String obj) throws IOException;

    public void returnJson(Object obj) throws IOException;

    public void setCookie(String key, String value, boolean httpOnly) ;

    public void setCookie(String key, String value);
}