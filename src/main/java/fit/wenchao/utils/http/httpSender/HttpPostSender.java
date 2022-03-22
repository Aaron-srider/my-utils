package fit.wenchao.utils.http.httpSender;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;

import java.util.Map;

public class HttpPostSender extends HttpSender {

    UrlEncodedFormEntity postEntity;

    public HttpPostSender(Map<String, String> paramMap, Map<String, String> headerMap, String url) {
        super(paramMap, headerMap, url);
    }

    protected void populateReq(HttpRequestBase httpReq) {
        addHeaders(httpReq);
        ((HttpPost) httpReq).setEntity(postEntity);
    }

    protected HttpRequestBase getHttpReq() {
        postEntity = getParamEntity(paramMap);
        urlWithParam = url;
        return new HttpPost(url);
    }
}