package fit.wenchao.utils.web.req;

import fit.wenchao.utils.proxy.Underlying;

import javax.servlet.http.*;


public class HttpServletReqEnhanceImpl extends HttpServletReqEnhanceBase  {

    @Underlying
    HttpServletRequest req;

    //实现类必须有一个无参构造，泛型创建对象
    public HttpServletReqEnhanceImpl() {
    }

    public HttpServletReqEnhanceImpl(HttpServletRequest req) {
        this.req = req;
    }

    public String getRelativePath() {
        String requestURI = req.getRequestURI();
        //如果context-path未被设置或设置为"/"的话，则返回值是""；
        String contextPath = req.getContextPath();
        String relativePath = requestURI.substring(contextPath.length());
        return relativePath;
    }

    public Cookie getCookie(String cookieName) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public boolean isAjaxRequest() {
        String ajaxHeader = req.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(ajaxHeader)) {
            return true;
        }
        return false;
    }

    public String generateURI(String relativePath) {
        String contextPath = req.getContextPath();
        return contextPath + relativePath;
    }


}
