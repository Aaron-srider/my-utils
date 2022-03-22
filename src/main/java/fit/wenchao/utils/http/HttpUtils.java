package fit.wenchao.utils.http;

import fit.wenchao.utils.string.StrUtils;
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

import static fit.wenchao.utils.string.StrUtils.ft;


public class HttpUtils {
    static String cookie = "__jda=122270672.670814532.1641535937.1643285220.1647000066.5; __jdu=670814532; shshshfp=0f5905e8a267300e339b694aa2a55471; shshshfpa=fa7a2a2c-850d-4325-6675-3ede7734a0eb-1641535938; shshshfpb=gU3nU5Q2kMPUlsc-aYye12Q; TrackID=1x_-N9o4aN2av6uzcQlOtWU9uiFLI9mX2eMlJERbOvaW_fMG2O2J-K-o6EbRn7GaRfG3JKoctFZnN4glEyQFpamA44Rk7MaBDMwRfL3TSqByu_t5F-9vCxcPdeg019gg6; pinId=gxi2IkMj_NyJgBk7fdmRXw; __jdb=122270672.59.670814532|5.1647000066; __jdc=122270672; __jdv=76161171|cn.bing.com|-|referral|-|1647000065796; areaId=21; ipLoc-djd=21-1911-1924-26794; PCSYCityID=CN_360000_360700_0; shshshsID=d94fcc7d11e3dabb0392555705746b40_52_1647006830150; token=b9f8266a4bd9b3a75c498db903178b38,2,915003; __tk=819856128f1f726e120f126347229dd6,2,915003; ip_cityCode=1911; wlfstk_smdl=68vug8bhf5m13x1k3t2d966ov6f797tf; 3AB9D23F7A4B3C9B=36RHMT7PGJY7ZMCOCLCTNFATJBIDSRFILIMYWXE4YKI3AJJBXWO6VX5CFQWG3VZ3EWDGIF7UBMH7DUJYTMDL4ZYYGM";

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





