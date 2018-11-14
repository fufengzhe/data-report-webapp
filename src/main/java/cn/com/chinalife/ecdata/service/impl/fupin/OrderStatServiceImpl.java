package cn.com.chinalife.ecdata.service.impl.fupin;

import cn.com.chinalife.ecdata.dao.sqlDao.fupin.OrderStatDao;
import cn.com.chinalife.ecdata.entity.IPInfo;
import cn.com.chinalife.ecdata.entity.fupin.OrderStat;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.service.fupin.OrderStatService;
import cn.com.chinalife.ecdata.utils.*;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
@Service
public class OrderStatServiceImpl implements OrderStatService {
    private final Logger logger = LoggerFactory.getLogger(OrderStatServiceImpl.class);
    @Autowired
    OrderStatDao orderStatDao;

    public List<OrderStat> getOrderStatListForTimeSpan(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.fupinDataSource);
        List<OrderStat> orderStatList = orderStatDao.getOrderStatListForTimeSpan(queryPara);
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        logger.info("service返回结果为 {}", JSON.toJSONString(orderStatList));
        return orderStatList;
    }

    public int updateOrderStat(List<OrderStat> orderStatList) {
        if (orderStatList != null && orderStatList.size() > 0) {
            return orderStatDao.updateOrderStat(orderStatList);
        } else {
            return 0;
        }
    }

    public List<List<OrderStat>> getOrderStatList() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        List<List<OrderStat>> lists = new ArrayList<List<OrderStat>>();
        QueryPara queryPara = new QueryPara();
        queryPara.setStartDate(DateUtils.getYesterday());
        queryPara.setEndDate(DateUtils.getYesterday());
        List<OrderStat> orderStatListOfDate = this.getOrderStatListForTimeSpanFromStatTable(queryPara);
        lists.add(orderStatListOfDate);

        queryPara.setStartDate(DateUtils.getBeforeXDay(7));
        queryPara.setEndDate(DateUtils.getBeforeXDay(1));
        List<OrderStat> orderStatListForTrendOfDate = this.getOrderStatListForTimeSpanTrendFromStatTable(queryPara);
        lists.add(orderStatListForTrendOfDate);
        logger.info("service返回结果为 {}", JSON.toJSONString(lists));
        return lists;
    }

    public List<OrderStat> getOrderStatListForTimeSpanFromStatTable(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.fupinDataSource);
        queryPara.setWhereCondition(CommonUtils.getWhereConditionUsingPara(queryPara.getWhereCondition()));
        List<OrderStat> orderStatList = orderStatDao.getOrderStatListForTimeSpanFromStatTable(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(orderStatList));
        return orderStatList;
    }

    public List<OrderStat> getOrderProductList() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.fupinDataSource);
        List<OrderStat> orderProductList = orderStatDao.getOrderProductList();
        logger.info("service返回结果为 {}", JSON.toJSONString(orderProductList));
        return orderProductList;
    }

    public List<OrderStat> getPageClickIPInfoList(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<String> sellerIDList = orderStatDao.getFuPinSellerIDList();
        String sellerIDFilter = getSellerFilterUsingList(sellerIDList);
        queryPara.setWhereCondition(sellerIDFilter);
        List<OrderStat> onlineOrderIPList = new ArrayList<OrderStat>();
        DataSourceContextHolder.setDbType(CommonConstant.fupinDataSource);
        // 线上零售订单
        List<OrderStat> onlineRetailOrderIPList = orderStatDao.getOnlineRetailOrderIPList(queryPara);
        onlineOrderIPList.addAll(onlineRetailOrderIPList);
        // 线上集采订单
        List<OrderStat> onlineGroupBuyOrderIPList = orderStatDao.getOnlineGroupBuyOrderIPList(queryPara);
        onlineOrderIPList.addAll(onlineGroupBuyOrderIPList);
        List<OrderStat> orderStatList = this.getDistributeInfoUsingOrderIPList(onlineOrderIPList);
        logger.info("service返回结果为 {}", JSON.toJSONString(orderStatList));
        return orderStatList;
    }


    private List<OrderStat> getDistributeInfoUsingOrderIPList(List<OrderStat> onlineOrderIPList) {
        List<OrderStat> ipDistributeInfoList = new ArrayList<OrderStat>();
        Map<String, Integer> keyAndValue = new HashMap<String, Integer>();
        logger.info("开始调用第三方接口,需要处理的list大小为 {}", onlineOrderIPList.size());
        for (int i = 0; i < onlineOrderIPList.size(); i++) {
            OrderStat orderStat = onlineOrderIPList.get(i);
            IPInfo ipInfo = IPInfoUtils.getIPInfoList(orderStat.getIp(), 1);
            if (ipInfo != null) {
                StringBuilder keyOfLocation = new StringBuilder(orderStat.getStatDate()).append("&").append(orderStat.getStatDateSpan()).append("&").
                        append(CommonConstant.statIndexNameOfFupinOrderIPInfo).append("&").append(1).append("&").append(ipInfo.getProvince());
                StringBuilder keyOfCompany = new StringBuilder(orderStat.getStatDate()).append("&").append(orderStat.getStatDateSpan()).append("&").
                        append(CommonConstant.statIndexNameOfFupinOrderIPInfo).append("&").append(2).append("&").append(ipInfo.getCompany());
                Integer locationValue = keyAndValue.get(keyOfLocation.toString());
                if (locationValue == null) {
                    locationValue = 0;
                }
                keyAndValue.put(keyOfLocation.toString(), ++locationValue);
                Integer companyValue = keyAndValue.get(keyOfCompany.toString());
                if (companyValue == null) {
                    companyValue = 0;
                }
                keyAndValue.put(keyOfCompany.toString(), ++companyValue);
            }
            if (i % 100 == 0) {
                logger.info("第三方接口调用中，调用次数为 {}", i);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("线程暂停异常，异常信息为", e);
            }
        }
        for (Map.Entry<String, Integer> entry : keyAndValue.entrySet()) {
            String[] key = entry.getKey().split("&");
            Integer value = entry.getValue();
            OrderStat orderStat = new OrderStat();
            orderStat.setStatDate(key[0]);
            orderStat.setStatDateSpan(key[1]);
            orderStat.setIndexName(key[2]);
            orderStat.setDisType(key[3]);
            orderStat.setDisName(key[4]);
            orderStat.setIndexValue(value);
            ipDistributeInfoList.add(orderStat);
        }
        return ipDistributeInfoList;
    }

    private String getSellerFilterUsingList(List<String> sellerIDList) {
        StringBuilder sellerIDFilter = new StringBuilder(" (");
        if (sellerIDList != null && sellerIDList.size() > 0) {
            for (int i = 0; i < sellerIDList.size() - 1; i++) {
                sellerIDFilter.append(sellerIDList.get(i)).append(",");
            }
            sellerIDFilter.append(sellerIDList.get(sellerIDList.size() - 1)).append(")");
            return sellerIDFilter.toString();
        } else {
            return null;
        }
    }

    public int updateOrderIPInfo(List<OrderStat> orderIPInfoList) {
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        if (orderIPInfoList != null && orderIPInfoList.size() > 0) {
            return orderStatDao.updateOrderIPInfo(orderIPInfoList);
        } else {
            return 0;
        }
    }

    public List<List<OrderStat>> getOrderStatIPDistributeList(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<List<OrderStat>> orderStatList = new ArrayList<List<OrderStat>>();
        List<OrderStat> orderStatCompanyDistributeList = orderStatDao.getOrderStatCompanyDistributeList(queryPara);
        orderStatList.add(orderStatCompanyDistributeList);
        List<OrderStat> orderStatLocationDistributeList = orderStatDao.getOrderStatLocationDistributeList(queryPara);
        orderStatList.add(orderStatLocationDistributeList);
        logger.info("service返回结果为 {}", JSON.toJSONString(orderStatList));
        return orderStatList;
    }

    private List<OrderStat> getOrderStatListForTimeSpanTrendFromStatTable(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.fupinDataSource);
        List<OrderStat> orderStatList = orderStatDao.getOrderStatListForTimeSpanTrendFromStatTable(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(orderStatList));
        return orderStatList;
    }

}
