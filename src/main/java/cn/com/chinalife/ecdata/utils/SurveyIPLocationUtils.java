package cn.com.chinalife.ecdata.utils;

import cn.com.chinalife.ecdata.entity.IPInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiexiangyu on 2018/5/9.
 */
public class SurveyIPLocationUtils {
    private static CloseableHttpClient httpClient = HttpClients.createDefault();
    private static CloseableHttpClient httpClientWithProxy = HttpClients.custom().setDefaultRequestConfig(RequestConfig.custom().setProxy(
            new HttpHost("10.7.1.117", 3128)
    ).build()).build();
    private static List<String> failedIPList = new ArrayList<String>();


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

    public static List<String> getIPListFromFile(String fileName) throws Exception {
        File file = new File(SurveyIPLocationUtils.class.getResource("/file").getPath(), fileName);
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        fileReader = new FileReader(file);
        bufferedReader = new BufferedReader(fileReader);
        String line = null;
        List<String> list = new ArrayList<String>();
        while ((line = bufferedReader.readLine()) != null) {
            list.add(line);
        }
        bufferedReader.close();
        fileReader.close();
        return list;
    }

    public static String getLocationInfoForIP(String ip) throws Exception {
        URIBuilder uriBuilder = new URIBuilder("http://www.matools.com/ip");
        CloseableHttpResponse response = null;
        try {
            response = doGet(uriBuilder, "inputIP", ip);
            String entity = EntityUtils.toString(response.getEntity());
            if (entity != null && (!entity.toLowerCase().contains("<!doctype html public"))) {
                JSONObject jsonObject = JSON.parseObject(entity);
                String address = jsonObject.get("adr").toString();
                return address;
            } else {
                failedIPList.add(ip);
                return null;
            }
        } catch (Exception exception) {
            failedIPList.add(ip);
            return null;
        }
    }

    public static void printIPLocationInfo() throws Exception {
        List<String> list = getIPListFromFile("IP-List.txt");
        List<IPInfo> ipInfoList = new ArrayList<IPInfo>();
        for (String ip : list) {
            Thread.sleep(500);
            String location = getLocationInfoForIP(ip);
            if (location != null) {
                System.out.println(ip + "\t" + location);
                IPInfo ipInfo = new IPInfo();
                ipInfo.setIp(ip);
                int colonIndex = location.indexOf("：");
                if (location.contains("北京") || location.contains("天津") || location.contains("上海") || location.contains("重庆")) {
                    int provinceIndex = location.indexOf("市");
                    String province = location.substring(colonIndex + 1, provinceIndex);
                    ipInfo.setProvince(province);
                    ipInfo.setCity(province);
                } else {
                    int provinceIndex = location.indexOf("省");
                    if (provinceIndex < 0) {
                        provinceIndex = location.indexOf("区");
                    }
                    int cityIndex = location.lastIndexOf("市");
                    ipInfo.setProvince(location.substring(colonIndex + 1, provinceIndex));
                    if (cityIndex < 0) {
                        ipInfo.setCity("未知");
                        failedIPList.add(ip);
                    } else {
                        ipInfo.setCity(location.substring(provinceIndex + 1, cityIndex));
                    }
                }
                System.out.println(ipInfo);
                ipInfoList.add(ipInfo);
            }
        }
        System.out.println("IP归属查询完毕");
        for (IPInfo ipInfo : ipInfoList) {
            System.out.println(ipInfo.getIp() + "\t" + ipInfo.getProvince() + "\t" + ipInfo.getCity());
        }
        System.out.println("未查到省份信息IP列表如下");
        for (String ip : failedIPList) {
            System.out.println(ip);
        }
    }


    public static void main(String[] args) throws Exception {
        printIPLocationInfo();
    }
}
