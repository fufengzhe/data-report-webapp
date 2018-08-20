package cn.com.chinalife.ecdata.utils;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.Map;

/**
 * Created by xiexiangyu on 2018/5/9.
 */
public class InvokeUtils {
    private static CloseableHttpClient httpClient = HttpClients.createDefault();
    private static CloseableHttpClient httpClientWithProxy = HttpClients.custom().setDefaultRequestConfig(RequestConfig.custom().setProxy(
            new HttpHost("10.7.1.117",3128)
    ).build()).build();

    public static CloseableHttpResponse doGet(String url, Map<String, String> paraNameAndValueMap) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);
        for (Map.Entry<String, String> entry : paraNameAndValueMap.entrySet()) {
            uriBuilder.addParameter(entry.getKey(), entry.getValue());
        }
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        return httpClient.execute(httpGet);
    }

    public static CloseableHttpResponse doGet(String url, String key, String value) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);
        uriBuilder.addParameter(key, value);
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        return httpClient.execute(httpGet);
    }

    public static CloseableHttpResponse doGet(URIBuilder uriBuilder, String key, String value) throws Exception {
        uriBuilder.setParameter(key, value);
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        return httpClient.execute(httpGet);
    }

    public static CloseableHttpResponse doGetWithProxy(URIBuilder uriBuilder, String key, String value) throws Exception {
        uriBuilder.setParameter(key, value);
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        return httpClientWithProxy.execute(httpGet);
    }


    public static void main(String[] args) throws Exception {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("tel", "15810604756");
//        map.put("mob", "13935994080");
        URIBuilder uriBuilder = new URIBuilder("http://ip.ws.126.net/ipquery");
        CloseableHttpResponse response = doGetWithProxy(uriBuilder, "ip", "220.195.65.184");
        String entity=EntityUtils.toString(response.getEntity());
        System.out.println(entity);
//        CloseableHttpResponse response1 = doGet(uriBuilder, "tel", "13935994080");
//        String entity1 = EntityUtils.toString(response1.getEntity(), "utf-8");
//        System.out.println(entity1);
//        System.out.println(entity.substring((entity.indexOf("catName") + 9), (entity.indexOf("telString") - 7)));
//        System.out.println(entity.substring((entity.indexOf("province") + 10), (entity.indexOf("catName") - 7)));
    }
}
