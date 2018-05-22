package cn.com.chinalife.ecdata.service.impl.location;

import cn.com.chinalife.ecdata.dao.noSqlDao.location.LocationAnalysisNoSqlDao;
import cn.com.chinalife.ecdata.dao.sqlDao.InitDao;
import cn.com.chinalife.ecdata.dao.sqlDao.location.LocationAnalysisDao;
import cn.com.chinalife.ecdata.entity.combine.AnalysisIndex;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.UserSource;
import cn.com.chinalife.ecdata.service.location.LocationAnalysisService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.DataSourceContextHolder;
import cn.com.chinalife.ecdata.utils.DateUtils;
import cn.com.chinalife.ecdata.utils.InvokeUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
@Service
public class LocationAnalysisServiceImpl implements LocationAnalysisService {
    private final Logger logger = LoggerFactory.getLogger(LocationAnalysisServiceImpl.class);
    @Autowired
    LocationAnalysisDao locationAnalysisDao;
    @Autowired
    LocationAnalysisNoSqlDao locationAnalysisNoSqlDao;
    @Autowired
    InitDao initDao;


    public int updateRegisterMobileDistribute(QueryPara queryPara) throws Exception {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<String> dateList = DateUtils.getDateList(queryPara.getStartDate(), queryPara.getEndDate());
        Map<String, String> codeAndName = this.getSourceCodeAndNameMap();
        URIBuilder uriBuilder = new URIBuilder("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm");
        int effectedRow = 0;
        int temp;
        for (String date : dateList) {
            QueryPara para = new QueryPara();
            para.setStartDate(date);
            para.setEndDate(date);
            para.setUserSource(queryPara.getUserSource());
            List<AnalysisIndex> mobileAndSourceList = locationAnalysisDao.getRegisterMobileAndSourceList(para);
            if (mobileAndSourceList != null && mobileAndSourceList.size() > 0) {
                List<AnalysisIndex> distributeInfo = this.getRegisterMobileDistributeInfoUsingList(mobileAndSourceList, codeAndName, uriBuilder);
                if (distributeInfo.size() > 0) {
                    temp = locationAnalysisDao.updateDistributeInfo(distributeInfo);
                    effectedRow += temp;
                }
            }
        }
        logger.info("service更新完成，受影响行数为 {}", effectedRow);
        return effectedRow;
    }

    public int updateActiveIPDistribute(QueryPara queryPara) throws Exception {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<String> dateList = DateUtils.getDateList(queryPara.getStartDate(), queryPara.getEndDate());
        Map<String, String> codeAndName = this.getNewSourceCodeAndNameMap();
        URIBuilder uriBuilder = new URIBuilder("http://ip.taobao.com/service/getIpInfo2.php");
        int effectedRow = 0;
        int temp;
        for (String date : dateList) {
            QueryPara para = new QueryPara();
            para.setStartDate(date);
            para.setEndDate(date);
            para.setUserSource(queryPara.getUserSource());
//            // 不去重的ip和渠道
//            List<AnalysisIndex> ipAndSourceList = locationAnalysisNoSqlDao.getActiveIPAndSourceList(para);
            List<AnalysisIndex> ipAndSourceList = locationAnalysisNoSqlDao.getDistinctActiveIPAndSourceList(para);
            if (ipAndSourceList != null && ipAndSourceList.size() > 0) {
                List<AnalysisIndex> distributeInfo = this.getActiveIPDistributeInfoUsingList(ipAndSourceList, codeAndName, uriBuilder);
                if (distributeInfo.size() > 0) {
                    temp = locationAnalysisDao.updateDistributeInfo(distributeInfo);
                    effectedRow += temp;
                }
            }
        }
        logger.info("service更新完成，受影响行数为 {}", effectedRow);
        return effectedRow;
    }


    private List<AnalysisIndex> getRegisterMobileDistributeInfoUsingList(List<AnalysisIndex> mobileAndSourceList, Map<String, String> codeAndName, URIBuilder uriBuilder) throws Exception {
        Map<String, Integer> map = new HashMap<String, Integer>();
        logger.info("开始调用第三方接口,需要处理的list大小为 {}", mobileAndSourceList.size());
        int invokeTimes = 0;
        for (AnalysisIndex analysisIndex : mobileAndSourceList) {
            CloseableHttpResponse response = InvokeUtils.doGetWithProxy(uriBuilder, "tel", analysisIndex.getMobile());
            if (invokeTimes % 100 == 0) {
                logger.info("调用次数 {}", invokeTimes);
            }
            invokeTimes++;
            String entity = EntityUtils.toString(response.getEntity(), "utf-8");
            JSONObject jsonObject = JSON.parseObject(entity.replace("__GetZoneResult_ = ", ""));
            Object locationObject = jsonObject.get("province");
            Object companyObject = jsonObject.get("catName");
            String source = codeAndName.get(analysisIndex.getIndexSource()) == null ? analysisIndex.getIndexSource() : codeAndName.get(analysisIndex.getIndexSource());
            if (locationObject != null) {
                String location = locationObject.toString();
                if (location != null && location.length() > 0) {
                    String keyOfLocation = new StringBuffer(analysisIndex.getStatDate()).append("&").append(CommonConstant.statTimeSpanOfDate).append("&").
                            append(CommonConstant.distributeIndexNameOfRegisterMobile).append("&").append(source).append("&").append("2").append("&").append(location).toString();
                    Integer locationValue = map.get(keyOfLocation);
                    if (locationValue == null) {
                        locationValue = 0;
                    }
                    map.put(keyOfLocation, ++locationValue);
                }
            }
            if (companyObject != null) {
                String company = companyObject.toString();
                if (company != null && company.length() > 0) {
                    String keyOfCompany = new StringBuffer(analysisIndex.getStatDate()).append("&").append(CommonConstant.statTimeSpanOfDate).append("&").
                            append(CommonConstant.distributeIndexNameOfRegisterMobile).append("&").append(source).append("&").append("1").append("&").append(company).toString();
                    Integer companyValue = map.get(keyOfCompany);
                    if (companyValue == null) {
                        companyValue = 0;
                    }
                    map.put(keyOfCompany, ++companyValue);
                }
            }
        }
        logger.info("结束调用第三方接口");
        return this.getDistributeInfoListUsingMap(map);
    }

    private List<AnalysisIndex> getActiveIPDistributeInfoUsingList(List<AnalysisIndex> ipAndSourceList, Map<String, String> codeAndName, URIBuilder uriBuilder) throws Exception {
        Map<String, Integer> map = new HashMap<String, Integer>();
        logger.info("开始调用第三方接口,需要处理的list大小为 {}", ipAndSourceList.size());
        int invokeTimes = 0;
        for (AnalysisIndex analysisIndex : ipAndSourceList) {
            CloseableHttpResponse response = InvokeUtils.doGetWithProxy(uriBuilder, "ip", analysisIndex.getIp());
            if (invokeTimes % 100 == 0) {
                logger.info("调用次数 {}", invokeTimes);
            }
            invokeTimes++;
            JSONObject jsonObject = JSON.parseObject(EntityUtils.toString(response.getEntity()));
            if ("0".equals(jsonObject.getString("code"))) {
                JSONObject data = (JSONObject) jsonObject.get("data");
                Object locationObject = data.get("region");
                Object companyObject = data.get("isp");
                String source = codeAndName.get(analysisIndex.getIndexSource()) == null ? analysisIndex.getIndexSource() : codeAndName.get(analysisIndex.getIndexSource());
                if (locationObject != null) {
                    String location = locationObject.toString();
                    if (location != null && location.length() > 0) {
                        String keyOfLocation = new StringBuffer(analysisIndex.getStatDate()).append("&").append(CommonConstant.statTimeSpanOfDate).append("&").
                                append(CommonConstant.distributeIndexNameOfActiveIP).append("&").append(source).append("&").append("2").append("&").append(location).toString();
                        Integer locationValue = map.get(keyOfLocation);
                        if (locationValue == null) {
                            locationValue = 0;
                        }
                        map.put(keyOfLocation, ++locationValue);
                    }
                }
                if (companyObject != null) {
                    String company = companyObject.toString();
                    if (company != null && company.length() > 0) {
                        String keyOfCompany = new StringBuffer(analysisIndex.getStatDate()).append("&").append(CommonConstant.statTimeSpanOfDate).append("&").
                                append(CommonConstant.distributeIndexNameOfActiveIP).append("&").append(source).append("&").append("1").append("&").append(company).toString();
                        Integer companyValue = map.get(keyOfCompany);
                        if (companyValue == null) {
                            companyValue = 0;
                        }
                        map.put(keyOfCompany, ++companyValue);
                    }
                }
            }
        }
        logger.info("结束调用第三方接口");
        return this.getDistributeInfoListUsingMap(map);
    }

    private List<AnalysisIndex> getDistributeInfoListUsingMap(Map<String, Integer> map) {
        List<AnalysisIndex> distributeInfoList = new ArrayList<AnalysisIndex>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            AnalysisIndex analysisIndex = new AnalysisIndex();
            String key = entry.getKey();
            Integer indexValue = entry.getValue();
            String[] keys = key.split("&");
            analysisIndex.setStatDate(keys[0]);
            analysisIndex.setStatDateSpan(keys[1]);
            analysisIndex.setIndexName(keys[2]);
            analysisIndex.setIndexSource(keys[3]);
            analysisIndex.setDistributeType(keys[4]);
            analysisIndex.setDistributeName(keys[5]);
            analysisIndex.setIndexValue(indexValue);
            distributeInfoList.add(analysisIndex);
        }
        return distributeInfoList;
    }

    private Map<String, String> getSourceCodeAndNameMap() {
        Map<String, String> codeAndName = new HashMap<String, String>();
        List<UserSource> userSourceList = initDao.getOldUserSourceOfAll();
        for (UserSource userSource : userSourceList) {
            codeAndName.put(userSource.getUserSource(), userSource.getUserSourceName());
        }
        return codeAndName;
    }

    private Map<String, String> getNewSourceCodeAndNameMap() {
        Map<String, String> codeAndName = new HashMap<String, String>();
        List<UserSource> userSourceList = initDao.getNewUserSourceOfAll();
        for (UserSource userSource : userSourceList) {
            codeAndName.put(userSource.getUserSource(), userSource.getUserSourceName());
        }
        return codeAndName;
    }

    public List<List<AnalysisIndex>> getRegisterMobileDistributeInfo(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        List<List<AnalysisIndex>> analysisIndexList = new ArrayList<List<AnalysisIndex>>();
        this.setWhereConditionUsingPara(queryPara.getUserSource(), queryPara);
        queryPara.setDistributeType("1");
        List<AnalysisIndex> registerMobileCompanyDistribute = locationAnalysisDao.getRegisterMobileDistributeInfo(queryPara);
        analysisIndexList.add(registerMobileCompanyDistribute);
        queryPara.setDistributeType("2");
        List<AnalysisIndex> registerMobileLocationDistribute = locationAnalysisDao.getRegisterMobileDistributeInfo(queryPara);
        analysisIndexList.add(registerMobileLocationDistribute);
        logger.info("service返回结果为 {}", JSON.toJSONString(analysisIndexList));
        return analysisIndexList;
    }

    public List<AnalysisIndex> getUserSourceList() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        List<AnalysisIndex> analysisIndexList = locationAnalysisDao.getUserSourceList();
        logger.info("service返回结果为 {}", JSON.toJSONString(analysisIndexList));
        return analysisIndexList;
    }

    public List<List<AnalysisIndex>> getActiveIPDistributeInfo(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        List<List<AnalysisIndex>> analysisIndexList = new ArrayList<List<AnalysisIndex>>();
        this.setWhereConditionUsingPara(queryPara.getUserSource(), queryPara);
        queryPara.setDistributeType("1");
        List<AnalysisIndex> activeIPCompanyDistribute = locationAnalysisDao.getActiveIPDistributeInfo(queryPara);
        analysisIndexList.add(activeIPCompanyDistribute);
        queryPara.setDistributeType("2");
        List<AnalysisIndex> activeIPLocationDistribute = locationAnalysisDao.getActiveIPDistributeInfo(queryPara);
        analysisIndexList.add(activeIPLocationDistribute);
        logger.info("service返回结果为 {}", JSON.toJSONString(analysisIndexList));
        return analysisIndexList;
    }

    public int updateActiveTimeDis(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        Map<String, String> codeAndName = this.getNewSourceCodeAndNameMap();
        int effectedRow = 0;
        List<AnalysisIndex> activeTimeDisList = locationAnalysisNoSqlDao.getActiveTimeDis(queryPara);
        for (AnalysisIndex analysisIndex : activeTimeDisList) {
            analysisIndex.setIndexSource(codeAndName.get(analysisIndex.getIndexSource()) == null ? analysisIndex.getIndexName() : codeAndName.get(analysisIndex.getIndexSource()));
        }
        if (activeTimeDisList != null && activeTimeDisList.size() > 0) {
            effectedRow = locationAnalysisDao.updateDistributeInfo(activeTimeDisList);
        }
        logger.info("service更新完成，受影响行数为 {}", effectedRow);
        return effectedRow;
    }

    public int updateUserCollectionInvokeDis(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        Map<String, String> codeAndName = this.getNewSourceCodeAndNameMap();
        int effectedRow = 0;
        List<AnalysisIndex> userCollectionInvokeDisList = locationAnalysisNoSqlDao.getUserCollectionInvokeDis(queryPara);
        for (AnalysisIndex analysisIndex : userCollectionInvokeDisList) {
            analysisIndex.setIndexSource(codeAndName.get(analysisIndex.getIndexSource()) == null ? analysisIndex.getIndexSource() : codeAndName.get(analysisIndex.getIndexSource()));
        }
        if (userCollectionInvokeDisList != null && userCollectionInvokeDisList.size() > 0) {
            effectedRow = locationAnalysisDao.updateDistributeInfo(userCollectionInvokeDisList);
        }
        logger.info("service更新完成，受影响行数为 {}", effectedRow);
        return effectedRow;
    }

    public List<List<AnalysisIndex>> getActiveHourAndUserCollDisInfo(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        List<List<AnalysisIndex>> analysisIndexList = new ArrayList<List<AnalysisIndex>>();
        this.setWhereConditionUsingPara(queryPara.getUserSource(), queryPara);
        queryPara.setDistributeType("3");
        List<AnalysisIndex> activeHourList = locationAnalysisDao.getActiveHourDisInfo(queryPara);
        analysisIndexList.add(activeHourList);
        queryPara.setDistributeType("4");
        List<AnalysisIndex> userCollectionFunList = locationAnalysisDao.getUserCollectionDisInfo(queryPara);
        for (AnalysisIndex analysisIndex : userCollectionFunList) {
            String distributeName = analysisIndex.getDistributeName();
            int index = distributeName.indexOf("Impl.");
            analysisIndex.setDistributeName(distributeName.substring(index + 5));
        }
        analysisIndexList.add(userCollectionFunList);
        queryPara.setDistributeType("5");
        List<AnalysisIndex> userCollectionReturnList = locationAnalysisDao.getUserCollectionDisInfo(queryPara);
        analysisIndexList.add(userCollectionReturnList);
        logger.info("service返回结果为 {}", JSON.toJSONString(analysisIndexList));
        return analysisIndexList;
    }

    public int updateMigrateCollection(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        Map<String, String> codeAndName = this.getNewSourceCodeAndNameMap();
        int effectedRow = 0;
        List<AnalysisIndex> migrateDisList = locationAnalysisNoSqlDao.getMigrateDisInfo(queryPara);
        for (AnalysisIndex analysisIndex : migrateDisList) {
            analysisIndex.setIndexSource(codeAndName.get(analysisIndex.getIndexSource()) == null ? analysisIndex.getIndexName() : codeAndName.get(analysisIndex.getIndexSource()));
            analysisIndex.setDistributeName(codeAndName.get(analysisIndex.getDistributeName()) == null ? analysisIndex.getDistributeName() : codeAndName.get(analysisIndex.getDistributeName()));
        }
        if (migrateDisList != null && migrateDisList.size() > 0) {
            effectedRow = locationAnalysisDao.updateDistributeInfo(migrateDisList);
        }
        logger.info("service更新完成，受影响行数为 {}", effectedRow);
        return effectedRow;
    }

    public List<List<AnalysisIndex>> getMigrateCollectionDis(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        List<List<AnalysisIndex>> analysisIndexList = new ArrayList<List<AnalysisIndex>>();
        queryPara.setFromUserSource(this.getWhereConditionUsingPara(queryPara.getFromUserSource()));
        queryPara.setToUserSource(this.getWhereConditionUsingPara(queryPara.getToUserSource()));
        queryPara.setDistributeType("6");
        List<AnalysisIndex> migrateCollectionDisList = locationAnalysisDao.getMigrateCollectionDis(queryPara);
        analysisIndexList.add(migrateCollectionDisList);
        List<AnalysisIndex> migrateCollectionFromDisList = locationAnalysisDao.getMigrateCollectionFromDis(queryPara);
        List<AnalysisIndex> migrateCollectionToDisList = locationAnalysisDao.getMigrateCollectionToDis(queryPara);
        List<AnalysisIndex> fromAndToList = this.mergeFromAndToList(migrateCollectionFromDisList, migrateCollectionToDisList);
        analysisIndexList.add(fromAndToList);
        logger.info("service返回结果为 {}", JSON.toJSONString(analysisIndexList));
        return analysisIndexList;
    }

    private List<AnalysisIndex> mergeFromAndToList(List<AnalysisIndex> migrateCollectionFromDisList, List<AnalysisIndex> migrateCollectionToDisList) {
        List<AnalysisIndex> analysisIndexList = new ArrayList<AnalysisIndex>();
        Map<String, Integer> fromMap = new HashMap<String, Integer>();
        Map<String, Integer> toMap = new HashMap<String, Integer>();
        Set<String> sourceSet = new HashSet<String>();
        for (AnalysisIndex analysisIndex : migrateCollectionFromDisList) {
            sourceSet.add(analysisIndex.getFromUserSource());
            fromMap.put(analysisIndex.getFromUserSource(), analysisIndex.getIndexValue());
        }
        for (AnalysisIndex analysisIndex : migrateCollectionToDisList) {
            sourceSet.add(analysisIndex.getToUserSource());
            toMap.put(analysisIndex.getToUserSource(), analysisIndex.getIndexValue());
        }
        for (String source : sourceSet) {
            AnalysisIndex analysisIndex = new AnalysisIndex();
            analysisIndex.setIndexSource(source);
            Integer fromIndex = fromMap.get(source);
            analysisIndex.setFromIndexValue(fromIndex == null ? 0 : fromIndex);
            Integer toIndex = toMap.get(source);
            analysisIndex.setToIndexValue(toIndex == null ? 0 : toIndex);
            analysisIndexList.add(analysisIndex);
        }
        return analysisIndexList;
    }

    private void setWhereConditionUsingPara(String userSource, QueryPara queryPara) {
        if (userSource != null) {
            String[] temp = userSource.split(",");
            StringBuilder userSourceCondition = new StringBuilder("(");
            for (int i = 0; i < temp.length - 1; i++) {
                userSourceCondition.append("'").append(temp[i]).append("',");
            }
            userSourceCondition.append("'").append(temp[temp.length - 1]).append("')");
            queryPara.setUserSource(userSourceCondition.toString());
            queryPara.setFromUserSource(userSourceCondition.toString());
            queryPara.setUserSource(userSourceCondition.toString());
        }
    }

    private String getWhereConditionUsingPara(String userSource) {
        if (userSource != null) {
            String[] temp = userSource.split(",");
            StringBuilder userSourceCondition = new StringBuilder("(");
            for (int i = 0; i < temp.length - 1; i++) {
                userSourceCondition.append("'").append(temp[i]).append("',");
            }
            userSourceCondition.append("'").append(temp[temp.length - 1]).append("')");
            return userSourceCondition.toString();
        } else {
            return null;
        }
    }

}
