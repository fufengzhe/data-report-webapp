package cn.com.chinalife.ecdata.service.impl.fupin;

import cn.com.chinalife.ecdata.dao.sqlDao.fupin.OrderStatDao;
import cn.com.chinalife.ecdata.entity.IPInfo;
import cn.com.chinalife.ecdata.entity.fupin.OrderStat;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.service.fupin.OrderStatService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.CommonUtils;
import cn.com.chinalife.ecdata.utils.DataSourceContextHolder;
import cn.com.chinalife.ecdata.utils.IPInfoUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

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

    public List<List<OrderStat>> getOrderStatList(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        List<List<OrderStat>> lists = new ArrayList<List<OrderStat>>();
        List<OrderStat> orderAmountAreaDimensionList = this.getOrderAmountListForAreaDimension(queryPara);
        lists.add(orderAmountAreaDimensionList);
        logger.info("service返回结果为 {}", JSON.toJSONString(lists));
        return lists;
    }

    private List<OrderStat> getOrderAmountListForAreaDimension(QueryPara queryPara) {
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<String> sellerIDList = orderStatDao.getFuPinSellerIDList();
        String sellerIDFilter = getSellerFilterUsingList(sellerIDList);
        queryPara.setWhereCondition(sellerIDFilter);
        List<String> sellerNameList = orderStatDao.getFuPinSellerNameList();
        String sellerNameFilter = getSellerFilterUsingList(sellerNameList);
        queryPara.setWhereCondition1(sellerNameFilter);
        List<OrderStat> sellerList = orderStatDao.getFuPinSellerAreaList();
        DataSourceContextHolder.setDbType(CommonConstant.fupinDataSource);
        //只包含线上零售和线上集采的部分，因为线下邮件所用供应商因人工录入可能会导致问题，所以线下邮件部分直接取地区维度的
        List<OrderStat> orderAmountSellerDimensionOfOnlineList = orderStatDao.getOrderAmountListForSellerDimension(queryPara);
        List<OrderStat> orderAmountAreaOfOfflineMailList = orderStatDao.getOrderAmountListForAreaOfOfflineMail(queryPara);
        Map<String, String> sellerNameAndAreaMap = new HashMap<String, String>();
        for (OrderStat orderStat : sellerList) {
            sellerNameAndAreaMap.put(orderStat.getSellerName(), orderStat.getArea());
        }
        Map<String, OrderStat> areaStatMap = new HashMap<String, OrderStat>();
        //将线上零售和线上集采部分统计到地区维度
        for (OrderStat orderStat : orderAmountSellerDimensionOfOnlineList) {
            String sellerName = orderStat.getSellerName();
            String area = sellerNameAndAreaMap.get(sellerName) == null ? sellerName : sellerNameAndAreaMap.get(sellerName);
            OrderStat temp = areaStatMap.get(area);
            if (temp == null) {
                temp = new OrderStat();
                temp.setArea(area);
                temp.setOrderNum(orderStat.getOrderNum());
                temp.setOrderAmount(orderStat.getOrderAmount());
                areaStatMap.put(area, temp);
            } else {
                temp.setOrderNum(orderStat.getOrderNum() + temp.getOrderNum());
                temp.setOrderAmount(orderStat.getOrderAmount().add(temp.getOrderAmount()));
            }
        }
        for (OrderStat orderStat : orderAmountAreaOfOfflineMailList) {
            String area = orderStat.getArea();
            OrderStat temp = areaStatMap.get(area);
            if (temp != null) {
                temp.setOrderNum(orderStat.getOrderNum() + temp.getOrderNum());
                temp.setOrderAmount(orderStat.getOrderAmount().add(temp.getOrderAmount()));
            } else {
                areaStatMap.put(area, orderStat);
            }
        }
        List<OrderStat> orderAmountAreaDimensionList = new ArrayList<OrderStat>();
        OrderStat sumAmount = new OrderStat();
        sumAmount.setArea("总计");
        sumAmount.setOrderNum(0);
        sumAmount.setOrderAmount(new BigDecimal("0.00"));
        OrderStat neiMengAmount = new OrderStat();
        Set<String> fupinSpecifiedAreaSet = this.getFupinSpecifiedArea();
        for (Map.Entry<String, OrderStat> entry : areaStatMap.entrySet()) {
            String key = entry.getKey();
            OrderStat value = entry.getValue();
            if (fupinSpecifiedAreaSet.contains(key)) {
                orderAmountAreaDimensionList.add(value);
                sumAmount.setOrderNum(value.getOrderNum() + sumAmount.getOrderNum());
                sumAmount.setOrderAmount(value.getOrderAmount().add(sumAmount.getOrderAmount()));
            } else if ("乌兰察布".equals(key)) {
                neiMengAmount = value;
            } else {
                orderAmountAreaDimensionList.add(value);
                sumAmount.setOrderNum(value.getOrderNum() + sumAmount.getOrderNum());
                sumAmount.setOrderAmount(value.getOrderAmount().add(sumAmount.getOrderAmount()));
            }
        }
        Collections.sort(orderAmountAreaDimensionList, new Comparator<OrderStat>() {
            public int compare(OrderStat o1, OrderStat o2) {
                return o2.getOrderAmount().subtract(o1.getOrderAmount()).compareTo(new BigDecimal("0.00"));
            }
        });
        orderAmountAreaDimensionList.add(0, sumAmount);
        orderAmountAreaDimensionList.add(neiMengAmount);
        for (OrderStat orderStat : orderAmountAreaDimensionList) {
            orderStat.setOrderAmount(CommonUtils.convertToTenThousandUnit(orderStat.getOrderAmount()));
        }
        sumAmount.setCompleteRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(sumAmount.getOrderAmount(), new BigDecimal("1800"), 2)));
        return orderAmountAreaDimensionList;
    }

    private Set<String> getFupinSpecifiedArea() {
        Set<String> fuPinSpecifiedSet = new HashSet<String>();
        fuPinSpecifiedSet.add("龙州");
        fuPinSpecifiedSet.add("郧西");
        fuPinSpecifiedSet.add("天等");
        fuPinSpecifiedSet.add("丹江口");
        return fuPinSpecifiedSet;
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

    private String getSellerFilterUsingList(List<String> sellerList) {
        StringBuilder sellerFilter = new StringBuilder(" (");
        if (sellerList != null && sellerList.size() > 0) {
            for (int i = 0; i < sellerList.size() - 1; i++) {
                sellerFilter.append("'").append(sellerList.get(i)).append("'").append(",");
            }
            sellerFilter.append("'").append(sellerList.get(sellerList.size() - 1)).append("'").append(")");
            return sellerFilter.toString();
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

    public List<OrderStat> getOrderFromToAreaInfoList(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<String> sellerIDList = orderStatDao.getFuPinSellerIDList();
        String sellerIDFilter = getSellerFilterUsingList(sellerIDList);
        queryPara.setWhereCondition(sellerIDFilter);
        DataSourceContextHolder.setDbType(CommonConstant.fupinDataSource);
        List<OrderStat> onlineOrderFromToAreaList = orderStatDao.getOnlineOrderFromToAreaList(queryPara);
        List<OrderStat> orderStatList = this.getDistributeInfoUsingOrderFromToAreaList(onlineOrderFromToAreaList);
        logger.info("service返回结果为 {}", JSON.toJSONString(orderStatList));
        return orderStatList;
    }


    private List<OrderStat> getDistributeInfoUsingOrderFromToAreaList(List<OrderStat> onlineOrderFromToAreaList) {
        List<OrderStat> fromToAreaDistributeInfoList = new ArrayList<OrderStat>();
        Map<String, Integer> keyAndValue = new HashMap<String, Integer>();
        logger.info("开始调用第三方接口,需要处理的list大小为 {}", onlineOrderFromToAreaList.size());
        for (int i = 0; i < onlineOrderFromToAreaList.size(); i++) {
            OrderStat orderStat = onlineOrderFromToAreaList.get(i);
            IPInfo ipInfo = IPInfoUtils.getIPInfoList(orderStat.getIp(), 2);
            if (ipInfo != null) {
                StringBuilder keyOfLocation = new StringBuilder(orderStat.getStatDate()).append("&").append("D").append("&").
                        append(CommonConstant.statIndexNameOfFupinOrderFromToAreaInfo).append("&").append(ipInfo.getProvince()).append("&").
                        append(orderStat.getProvince());
                Integer locationValue = keyAndValue.get(keyOfLocation.toString());
                if (locationValue == null) {
                    locationValue = 0;
                }
                keyAndValue.put(keyOfLocation.toString(), ++locationValue);
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
            orderStat.setFrom(key[3]);
            orderStat.setTo(key[4]);
            orderStat.setIndexValue(value);
            fromToAreaDistributeInfoList.add(orderStat);
        }
        return fromToAreaDistributeInfoList;
    }

    private List<OrderStat> getOrderStatListForTimeSpanTrendFromStatTable(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.fupinDataSource);
        List<OrderStat> orderStatList = orderStatDao.getOrderStatListForTimeSpanTrendFromStatTable(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(orderStatList));
        return orderStatList;
    }

    public int updateOrderFromToAreaInfo(List<OrderStat> orderFromToAreaInfoList) {
        if (orderFromToAreaInfoList != null && orderFromToAreaInfoList.size() > 0) {
            return orderStatDao.updateOrderFromToAreaInfo(orderFromToAreaInfoList);
        } else {
            return 0;
        }
    }

    public List<List<OrderStat>> getOrderFromToAreaFlowInfo(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<List<OrderStat>> orderStatList = new ArrayList<List<OrderStat>>();
        queryPara.setWhereCondition(CommonUtils.getWhereConditionUsingPara(queryPara.getWhereCondition()));
        queryPara.setWhereCondition1(CommonUtils.getWhereConditionUsingPara(queryPara.getWhereCondition1()));
        List<OrderStat> fromToList = orderStatDao.getOrderFromToInfoList(queryPara);
        orderStatList.add(fromToList);
        List<OrderStat> fromList = orderStatDao.getOrderFromInfoList(queryPara);
        List<OrderStat> toList = orderStatDao.getOrderToInfoList(queryPara);
        List<OrderStat> fromToSumList = this.mergeUsingList(fromList, toList);
        orderStatList.add(fromToSumList);
        logger.info("service返回结果为 {}", JSON.toJSONString(orderStatList));
        return orderStatList;
    }

    private List<OrderStat> mergeUsingList(List<OrderStat> fromList, List<OrderStat> toList) {
        List<OrderStat> orderStatList = new ArrayList<OrderStat>();
        Set<String> set = new HashSet<String>();
        Map<String, Integer> fromMap = new HashMap<String, Integer>();
        Map<String, Integer> toMap = new HashMap<String, Integer>();
        for (OrderStat orderStat : fromList) {
            set.add(orderStat.getSource());
            fromMap.put(orderStat.getSource(), orderStat.getIndexValue());
        }
        for (OrderStat orderStat : toList) {
            set.add(orderStat.getTarget());
            toMap.put(orderStat.getTarget(), orderStat.getIndexValue());
        }
        for (String area : set) {
            OrderStat orderStat = new OrderStat();
            orderStat.setSource(area);
            Integer fromIndex = fromMap.get(area);
            orderStat.setIndexValue(fromIndex == null ? 0 : fromIndex);
            Integer toIndex = toMap.get(area);
            orderStat.setOrderNum(toIndex == null ? 0 : toIndex);
            orderStatList.add(orderStat);
        }
        return orderStatList;
    }

    public List<OrderStat> getFromList(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<OrderStat> fromToList = orderStatDao.getFromList(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(fromToList));
        return fromToList;
    }

    public List<OrderStat> getToList(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<OrderStat> fromToList = orderStatDao.getToList(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(fromToList));
        return fromToList;
    }
}
