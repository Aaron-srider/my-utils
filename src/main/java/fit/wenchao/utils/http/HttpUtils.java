package fit.wenchao.utils.http;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import static fit.wenchao.utils.string.placeholderString.TemplateString.ft;


public class HttpUtils {

    private static class PoolingHttpClientConnectionManagerHolder {
        private static PoolingHttpClientConnectionManager pc = new PoolingHttpClientConnectionManager();

        static {
            pc.setMaxTotal(100);
        }
    }

    private static PoolingHttpClientConnectionManager getHttpClientPoolInstance() {
        return HttpUtils.PoolingHttpClientConnectionManagerHolder.pc;
    }

    public static HttpClient getHttpClient() {
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(getHttpClientPoolInstance()).build();
        return client;
    }

    public static HttpClient getHttpClient(CookieStore cookieStore) {
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(getHttpClientPoolInstance())
                .setDefaultCookieStore(cookieStore).build();
        return client;
    }

    public static String doGetHtml(String url) {
        HttpClientRequestBuilder reqBuilder = HttpClientRequestBuilder.getInstance()
                .get(url)
                .addHeader("User-Agent",
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:98.0) Gecko/20100101 Firefox/98.0");

        try (HttpClientResponseWrapper response = reqBuilder.send()) {
            if(response.isOk()) {
                return response.getEntityAsString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String doGetHtml(HttpClient httpClient, String url) {

        HttpGet httpGet = new HttpGet(url);

        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:98.0) Gecko/20100101 Firefox/98.0");

        //httpGet.addHeader("Cookie", cookie);
        try (CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpGet)) {
            if (response.getStatusLine().getStatusCode() == 200) {
                if (response.getEntity() != null) {
                    String content = EntityUtils.toString(response.getEntity(), "utf8");
                    return content;
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String doGetImg(HttpClient httpClient, String url) {
        HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpGet)) {
            if (response.getStatusLine().getStatusCode() == 200) {
                if (response.getEntity() != null) {
                    String imgName = url.substring(url.lastIndexOf("."));
                    String picName = UUID.randomUUID() + imgName;
                    String filename = ft("{}Users{}cw", File.separator, File.separator);
                    OutputStream outputStream = new FileOutputStream(filename);
                    response.getEntity().writeTo(outputStream);
                    return picName;
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}





