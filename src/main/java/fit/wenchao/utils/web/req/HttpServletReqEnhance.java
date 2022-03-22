package fit.wenchao.utils.web.req;

import fit.wenchao.utils.proxy.EnhanceInterface;
import fit.wenchao.utils.proxy.Underlying;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@EnhanceInterface
public interface HttpServletReqEnhance extends HttpServletRequest {
    public String getRelativePath();

    public Cookie getCookie(String cookieName);

    public boolean isAjaxRequest();

    public String generateURI(String relativePath);
}
