package cn.com.chinalife.ecdata.utils;

import cn.com.chinalife.ecdata.entity.IPInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;

/**
 * Created by xiexiangyu on 2018/10/31.
 */
public class IPInfoUtils {
    private static final Logger logger = LoggerFactory.getLogger(IPInfoUtils.class);

    //infoType 1表示归属地和运营商同时获取，infoType 2表示获取归属地，infoType 3表示获取运营商
    public static IPInfo getIPInfoList(String ip, Integer infoType) {
        IPInfo ipInfo;
        URIBuilder taoBaoUriBuilder = null;
        URIBuilder maToolsUriBuilder = null;
        try {
            taoBaoUriBuilder = new URIBuilder("http://ip.taobao.com/service/getIpInfo2.php");
            maToolsUriBuilder = new URIBuilder("http://www.matools.com/ip");
        } catch (URISyntaxException e) {
            logger.error("URI实例生成异常，异常信息为", e);
        }
        if (ip == null || ip.length() == 0) {
            return null;
        } else {
            ipInfo = IPInfoUtils.getIpInfoUsingTaoBaoInterface(taoBaoUriBuilder, ip, infoType);
            if (ipInfo == null) {
                ipInfo = IPInfoUtils.getIpInfoUsingMaToolsInterface(maToolsUriBuilder, ip, infoType);
            }
            return ipInfo;
        }
    }

    private static IPInfo getIpInfoUsingTaoBaoInterface(URIBuilder uriBuilder, String ip, Integer infoType) {
        if (ip == null || ip.length() == 0) {
            return null;
        } else {
            IPInfo ipInfo = null;
            try {
                CloseableHttpResponse response = InvokeUtils.doGetWithProxy(uriBuilder, "ip", ip);
                if (response != null) {
                    String entity = EntityUtils.toString(response.getEntity());
                    if (entity != null && (!entity.toLowerCase().contains("<!doctype html public"))) {
                        JSONObject jsonObject = JSON.parseObject(entity);
                        if (jsonObject != null && ("0".equals(jsonObject.getString("code")))) {
                            JSONObject data = (JSONObject) jsonObject.get("data");
                            if (data != null) {
                                Object provinceObject = data.get("region");
                                Object cityObject = data.get("city");
                                Object companyObject = data.get("isp");
                                if (provinceObject != null && cityObject != null && companyObject != null) {
                                    ipInfo = new IPInfo();
                                    if (1 == infoType) {
                                        ipInfo.setProvince(provinceObject.toString());
                                        ipInfo.setCity(cityObject.toString());
                                        ipInfo.setCompany(companyObject.toString());
                                    } else if (2 == infoType) {
                                        ipInfo.setProvince(provinceObject.toString());
                                        ipInfo.setCity(cityObject.toString());
                                    } else if (3 == infoType) {
                                        ipInfo.setCompany(companyObject.toString());
                                    }
                                    return ipInfo;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("调用淘宝接口获取IP归属地及运营商信息异常，异常信息为", e);
                return null;
            }
            return ipInfo;
        }
    }

    private static IPInfo getIpInfoUsingMaToolsInterface(URIBuilder uriBuilder, String ip, Integer infoType) {
        if (ip == null || ip.length() == 0) {
            return null;
        } else {
            IPInfo ipInfo = null;
            try {
                CloseableHttpResponse response = InvokeUtils.doGetWithProxy(uriBuilder, "inputIP", ip);
                if (response != null) {
                    String entity = EntityUtils.toString(response.getEntity());
                    if (entity != null && (!entity.toLowerCase().contains("<!doctype html public"))) {
                        JSONObject jsonObject = JSON.parseObject(entity);
                        if (jsonObject != null) {
                            Object address = jsonObject.get("adr");
                            if (address != null) {
                                String addressStr = address.toString();
                                logger.info("matools接口返回地址信息为 {}", addressStr);
                                int colonIndex = addressStr.indexOf("：");
                                int provinceIndex = 0;
                                int spaceIndex = addressStr.lastIndexOf(" ");
                                if (addressStr.contains("北京") || addressStr.contains("天津") || addressStr.contains("上海") || addressStr.contains("重庆")) {
                                    provinceIndex = addressStr.indexOf("市");
                                } else {
                                    provinceIndex = addressStr.indexOf("省");
                                    if (provinceIndex < 0) {
                                        provinceIndex = addressStr.indexOf("区");
                                    }
                                }
                                String province = addressStr.substring(colonIndex + 1, provinceIndex);
                                String company = addressStr.substring(spaceIndex + 1);
                                logger.info("matools接口返回地址信息解析结果为省={},运营商={}", province, company);
                                ipInfo = new IPInfo();
                                if (1 == infoType) {
                                    ipInfo.setProvince(province);
                                    ipInfo.setCompany(company);
                                } else if (2 == infoType) {
                                    ipInfo.setProvince(province);
                                } else if (3 == infoType) {
                                    ipInfo.setCompany(company);
                                }
                                return ipInfo;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("调用matools接口获取IP归属地及运营商信息异常，异常信息为", e);
                return null;
            }
            return ipInfo;
        }
    }

    public static void main(String[] args) throws Exception {
    }
}
