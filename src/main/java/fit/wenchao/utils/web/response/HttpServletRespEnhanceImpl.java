package fit.wenchao.utils.web.response;

import com.alibaba.fastjson.JSON;
import fit.wenchao.utils.proxy.Underlying;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class HttpServletRespEnhanceImpl extends HttpServletRespEnhanceBase{

    @Underlying
    HttpServletResponse resp;

    public HttpServletRespEnhanceImpl() {
    }

    public HttpServletRespEnhanceImpl(HttpServletResponse resp ) {
        this.resp = resp;
    }

    public void returnJson(String obj) throws IOException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(obj);
    }

    public void returnJson(Object obj) throws IOException {
        String s = JSON.toJSONString(obj);
        returnJson(s);
    }

    public void setCookie(String key, String value, boolean httpOnly) {
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(httpOnly);
        resp.addCookie(cookie);
    }

    public void setCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        resp.addCookie(cookie);
    }

}
