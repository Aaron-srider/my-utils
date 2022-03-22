package fit.wenchao.utils.http;

import fit.wenchao.crawlerhelloworld.utils.http.constant.HttpMethodEnum;
import fit.wenchao.utils.http.httpSender.HttpGetSender;
import fit.wenchao.utils.http.httpSender.HttpPostSender;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class HttpClientRequestBuilder {

    private String url;

    private HttpMethodEnum method;

    private Map<String, String> paramMap = new HashMap<>();
    private Map<String, String> headerMap = new HashMap<>();

    public HttpClientRequestBuilder get(String url) {
        this.method = HttpMethodEnum.GET;
        this.url = url;
        return this;
    }

    public static HttpClientRequestBuilder getInstance() {
        return new HttpClientRequestBuilder();
    }

    public HttpClientResponseWrapper send() {
        if (method.equals(HttpMethodEnum.GET)) {
            HttpGetSender httpGetSender = new HttpGetSender(paramMap, headerMap, url);
            return httpGetSender.send();
        } else if (method.equals(HttpMethodEnum.POST)) {
            HttpPostSender httpPostSender = new HttpPostSender(paramMap, headerMap, url);
            return httpPostSender.send();
        }
        return null;
    }

    public HttpClientRequestBuilder addHeader(String headerName, String value) {
        headerMap.put(headerName, value);
        return this;
    }

    public HttpClientRequestBuilder param(String key, String value) {
        paramMap.put(key, value);
        return this;
    }

    public HttpClientRequestBuilder post(String url) {
        this.method = HttpMethodEnum.POST;
        this.url = url;
        return this;
    }


}