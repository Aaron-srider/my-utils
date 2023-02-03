package fit.wenchao.utils.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.cookie.Cookie;
import org.apache.http.util.EntityUtils;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import static fit.wenchao.utils.basic.BasicUtils.*;
import static fit.wenchao.utils.string.placeholderString.TemplateString.ft;

public class HttpClientResponseWrapper implements Closeable {

    private CookieStore cookieStore;

    public List<Cookie> getCookies() {
        return cookieStore.getCookies();
    }

    private CloseableHttpResponse response;

    public HttpClientResponseWrapper(CloseableHttpResponse response) {
        this.response = response;
    }

    public HttpClientResponseWrapper(CloseableHttpResponse response, CookieStore cookieStore) {
        this.response = response;
        this.cookieStore = cookieStore;
    }

    public static void main(String[] args) throws Exception {
        String url = "http://localhost:8080/hello";
        HttpClientResponseWrapper send = HttpClientRequestBuilder.getInstance()
                                                                 .post(url)
                                                                 .send();


        Header contentType = send.response.getEntity()
                                          .getContentType();
        HeaderElement[] elements = contentType.getElements();
        System.out.println(ft("elements:{},contentType.value:{},contentType" +
                        ".name:{}", elements
                , contentType.getValue(), contentType.getName()));
        gloop(forArr(send.response.getEntity()
                                  .getContentType()
                                  .getElements()
        ), (i, e, s) -> {
            System.out.println(ft("contentTypeElem:{}", e));
        });

        JSONObject jsonEntity = send.getJsonEntity();
        System.out.println(jsonEntity);
        List<Cookie> cookies = send.getCookies();
        gloop(forList(cookies), (i, e, s) -> {
                    System.out.println(ft("cookie:{}", e));
                    System.out.println(ft("cookie name:{}", e.getName()));
                    System.out.println(ft("cookie value:{}", e.getValue()));

                }
        );
    }

    public CloseableHttpResponse getResponse() {
        return response;
    }

    public String getEntityAsString() throws IOException {
        if (response.getEntity() != null) {
            String content = EntityUtils.toString(response.getEntity(), "utf8");
            return content;
        }
        return null;
    }

    public boolean isOk() {
        return this.response.getStatusLine()
                            .getStatusCode() == 200;
    }


    @Override
    public void close() throws IOException {
        response.close();
    }

    public JSONObject getJsonEntity() throws IOException {
        Header contentType = response.getEntity()
                                     .getContentType();
        String value = contentType.getValue();
        if (value.contains("application/json")) {
            String entityAsString = this.getEntityAsString();
            JSONObject jsonObject = JSONObject.parseObject(entityAsString);
            return jsonObject;
        }
        else {
            throw new IllegalStateException("can not get content as application/json, " + "the actual contentType is: " + value);
        }
    }
}