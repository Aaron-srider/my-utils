package fit.wenchao.utils.http.httpSender;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

import java.util.Map;

public class HttpGetSender extends HttpSender {

    public HttpGetSender(Map<String, String> paramMap, Map<String, String> headerMap, String url) {
        super(paramMap, headerMap, url);
    }

    protected void populateReq(HttpRequestBase httpReq) {
        addHeaders(httpReq);
    }

    protected HttpRequestBase getHttpReq() {
        String paramStr = getParamStr(paramMap);

        if (!paramStr.equals("")) {
            urlWithParam = url + "?" + paramStr;
        } else {
            urlWithParam = url;
        }

        return new HttpGet(urlWithParam);
    }

}