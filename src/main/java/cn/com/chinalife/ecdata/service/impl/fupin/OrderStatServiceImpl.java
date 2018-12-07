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
        OrderStat danQuanWater = this.getDanQuanWaterOrderAmount(queryPara);
        danQuanWater.setOrderAmount(CommonUtils.convertToTenThousandUnit(danQuanWater.getOrderAmount()));
        orderAmountAreaDimensionList.add(danQuanWater);
        lists.add(orderAmountAreaDimensionList);
        List<OrderStat> orderAmountCompanyDimensionList = this.getOrderAmountListForCompanyDimension(queryPara);
        lists.add(orderAmountCompanyDimensionList);
        logger.info("service返回结果为 {}", JSON.toJSONString(lists));
        return lists;
    }

    private List<OrderStat> getOrderAmountListForCompanyDimension(QueryPara queryPara) {
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<String> sellerIDList = orderStatDao.getFuPinSellerIDListForSpecifiedArea(" ('丹江口','龙州','郧西','天等') ");
        String sellerIDFilter = getSellerFilterUsingList(sellerIDList);
        queryPara.setWhereCondition(sellerIDFilter);
        // 分公司后期补录发票抬头
        List<OrderStat> supplyBillTitleListFromOracle = orderStatDao.getSupplyBillTitleListFromOracle();
        List<String> orderNoList = new ArrayList<String>();
        for (OrderStat orderStat : supplyBillTitleListFromOracle) {
            orderNoList.add(orderStat.getOrderNo());
        }
        String orderNoFilter = getSellerFilterUsingList(orderNoList);
        queryPara.setWhereCondition1(orderNoFilter);
        DataSourceContextHolder.setDbType(CommonConstant.fupinDataSource);
        List<OrderStat> onlineRetailAndJiCaiList = orderStatDao.getOnlineRetailAndJiCaiList(queryPara);
        List<OrderStat> offlineMailList = orderStatDao.getOfflineMailList(queryPara);
        List<OrderStat> supplyBillTitleList = orderStatDao.getSupplyBillTitleList(queryPara);
        List<OrderStat> supplyList = this.fillBillTitleForSuppliedOrder(supplyBillTitleList, supplyBillTitleListFromOracle);
        List<OrderStat> companyOrderAmountList = this.dealMemberTileAndCompanyRelationUsingLists(onlineRetailAndJiCaiList, offlineMailList, supplyList);
        return companyOrderAmountList;
    }

    private List<OrderStat> fillBillTitleForSuppliedOrder(List<OrderStat> supplyBillTitleList, List<OrderStat> supplyBillTitleListFromOracle) {
        Map<String, String> orderNoAndTitle = new HashMap<String, String>();
        Map<String, OrderStat> titleAndOrderInfoMap = new HashMap<String, OrderStat>();
        for (OrderStat orderStat : supplyBillTitleListFromOracle) {
            orderNoAndTitle.put(orderStat.getOrderNo(), orderStat.getCompany());
        }
        for (OrderStat orderStat : supplyBillTitleList) {
            String company = orderNoAndTitle.get(orderStat.getOrderNo());
            OrderStat orderInfo = titleAndOrderInfoMap.get(company);
            if (orderInfo == null) {
                orderInfo = new OrderStat();
                orderInfo.setCompany(company);
                orderInfo.setOrderNum(1);
                orderInfo.setOrderAmount(orderStat.getOrderAmount());
                titleAndOrderInfoMap.put(company, orderInfo);
            } else {
                orderInfo.setOrderNum(orderInfo.getOrderNum() + 1);
                orderInfo.setOrderAmount(orderInfo.getOrderAmount().add(orderStat.getOrderAmount()));
            }
        }
        List<OrderStat> supplyList = new ArrayList<OrderStat>();
        for (Map.Entry<String, OrderStat> entry : titleAndOrderInfoMap.entrySet()) {
            supplyList.add(entry.getValue());
        }
        return supplyList;
    }

    private List<OrderStat> dealMemberTileAndCompanyRelationUsingLists(List<OrderStat> onlineRetailAndJiCaiList, List<OrderStat> offlineMailList, List<OrderStat> supplyList) {
        List<OrderStat> originalCompanyList = new ArrayList<OrderStat>();
        originalCompanyList.addAll(onlineRetailAndJiCaiList);
        originalCompanyList.addAll(offlineMailList);
        originalCompanyList.addAll(supplyList);
        List<OrderStat> companyList = new ArrayList<OrderStat>();
        Map<String, OrderStat> companyKeyWordAndOrderStatMap = this.generateCompanyKeyWordAndOrderStatMap();
        List<OrderStat> notIncludedList = new ArrayList<OrderStat>();
        for (OrderStat orderStat : originalCompanyList) {
            boolean isChinaLife = false;
            for (Map.Entry<String, OrderStat> entry : companyKeyWordAndOrderStatMap.entrySet()) {
                if (orderStat.getCompany().contains(entry.getKey())) {
                    isChinaLife = true;
                    OrderStat value = entry.getValue();
                    value.setOrderNum(value.getOrderNum() + orderStat.getOrderNum());
                    value.setOrderAmount(value.getOrderAmount().add(orderStat.getOrderAmount()));
                    break;
                }
            }
            if (!isChinaLife) {
                notIncludedList.add(orderStat);
            }
        }
        Map<String, OrderStat> companyAndOrderStatMap = new LinkedHashMap<String, OrderStat>();
        for (Map.Entry<String, OrderStat> entry : companyKeyWordAndOrderStatMap.entrySet()) {
            OrderStat orderStat = entry.getValue();
            OrderStat addedOrderStat = companyAndOrderStatMap.get(orderStat.getCompany());
            if (addedOrderStat == null) {
                companyAndOrderStatMap.put(orderStat.getCompany(), orderStat);
            } else {
                addedOrderStat.setOrderNum(orderStat.getOrderNum() + addedOrderStat.getOrderNum());
                addedOrderStat.setOrderAmount(orderStat.getOrderAmount().add(addedOrderStat.getOrderAmount()));
            }
        }
        OrderStat sum = new OrderStat("总计", 0, new BigDecimal(0.00), new BigDecimal("18000000"), "0.00%");
        for (Map.Entry<String, OrderStat> entry : companyAndOrderStatMap.entrySet()) {
            OrderStat orderStat = entry.getValue();
            // 寿险扶贫采购活动减去这些订单里面的补退金额
            if ("寿险公司".equals(orderStat.getCompany())) {
                orderStat.setOrderAmount(orderStat.getOrderAmount().add(new BigDecimal("608412.48")));
            }
            if (orderStat.getOrderAmountGoal() != null) {
                orderStat.setCompleteRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(orderStat.getOrderAmount(), orderStat.getOrderAmountGoal(), 2)));
                orderStat.setOrderAmountGoal(CommonUtils.convertToTenThousandUnit(orderStat.getOrderAmountGoal()));
            }
            sum.setOrderAmount(sum.getOrderAmount().add(orderStat.getOrderAmount()));
            sum.setOrderNum(sum.getOrderNum() + orderStat.getOrderNum());
            companyList.add(orderStat);
        }
        sum.setCompleteRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(sum.getOrderAmount(), sum.getOrderAmountGoal(), 2)));
        companyList.add(sum);
        companyList.addAll(notIncludedList);
        for (OrderStat orderStat : companyList) {
            orderStat.setOrderAmount(CommonUtils.convertToTenThousandUnit(orderStat.getOrderAmount()));
        }
        return companyList;
    }

    private Map<String, OrderStat> generateCompanyKeyWordAndOrderStatMap() {
        Map<String, OrderStat> orderStatMap = new LinkedHashMap<String, OrderStat>();
        orderStatMap.put("中国人寿保险（集团）公司", new OrderStat("集团公司", 0, new BigDecimal("0.00"), new BigDecimal("300000"), "0.00%"));
        orderStatMap.put("中国人寿保险股份", new OrderStat("寿险公司", 0, new BigDecimal("0.00"), new BigDecimal("12000000"), "0.00%"));
        orderStatMap.put("中国人寿股份", new OrderStat("寿险公司", 0, new BigDecimal("0.00"), new BigDecimal("12000000"), "0.00%"));
        orderStatMap.put("中国人寿保险（海外）股份", new OrderStat("寿险公司", 0, new BigDecimal("0.00"), new BigDecimal("12000000"), "0.00%"));
        orderStatMap.put("中国人寿绵阳市分公司工会", new OrderStat("寿险公司", 0, new BigDecimal("0.00"), new BigDecimal("12000000"), "0.00%"));
        orderStatMap.put("中国人寿保险", new OrderStat("寿险公司", 0, new BigDecimal("0.00"), new BigDecimal("12000000"), "0.00%"));
        orderStatMap.put("中国人寿无锡市分公司", new OrderStat("寿险公司", 0, new BigDecimal("0.00"), new BigDecimal("12000000"), "0.00%"));
        orderStatMap.put("中国共产党中国人寿四川省分公司机关委员会", new OrderStat("寿险公司", 0, new BigDecimal("0.00"), new BigDecimal("12000000"), "0.00%"));
        orderStatMap.put("广东荔湾大厦", new OrderStat("寿险公司", 0, new BigDecimal("0.00"), new BigDecimal("12000000"), "0.00%"));
        orderStatMap.put("中国人寿资产管理", new OrderStat("资产公司", 0, new BigDecimal("0.00"), new BigDecimal("1000000"), "0.00%"));
        orderStatMap.put("人寿财产保险", new OrderStat("财险公司", 0, new BigDecimal("0.00"), new BigDecimal("3800000"), "0.00%"));
        orderStatMap.put("人寿财险保险", new OrderStat("财险公司", 0, new BigDecimal("0.00"), new BigDecimal("3800000"), "0.00%"));
        orderStatMap.put("养老", new OrderStat("养老险公司", 0, new BigDecimal("0.00"), new BigDecimal("300000"), "0.00%"));
        orderStatMap.put("中国人寿电子商务", new OrderStat("电商公司", 0, new BigDecimal("0.00"), new BigDecimal("300000"), "0.00%"));
        orderStatMap.put("投资", new OrderStat("国寿投资公司", 0, new BigDecimal("0.00"), new BigDecimal("300000"), "0.00%"));
        orderStatMap.put("国寿嘉园", new OrderStat("国寿投资公司", 0, new BigDecimal("0.00"), new BigDecimal("300000"), "0.00%"));
        orderStatMap.put("保险职业学院", new OrderStat("保险职业学院", 0, new BigDecimal("0.00"), null, null));
        orderStatMap.put("安保基金", new OrderStat("安保基金", 0, new BigDecimal("0.00"), null, null));
        orderStatMap.put("财富管理", new OrderStat("财富管理", 0, new BigDecimal("0.00"), null, null));
        orderStatMap.put("广发银行", new OrderStat("广发银行", 0, new BigDecimal("0.00"), null, null));
        orderStatMap.put("绿洲", new OrderStat("绿洲酒店", 0, new BigDecimal("0.00"), null, null));
        orderStatMap.put("远通置业", new OrderStat("远通置业", 0, new BigDecimal("0.00"), null, null));
        orderStatMap.put("中保大厦", new OrderStat("中保大厦", 0, new BigDecimal("0.00"), null, null));
        orderStatMap.put("审计中心", new OrderStat("审计中心", 0, new BigDecimal("0.00"), null, null));
        orderStatMap.put("物业管理", new OrderStat("物业管理", 0, new BigDecimal("0.00"), null, null));
        orderStatMap.put("柠檬树", new OrderStat("集团餐厅", 0, new BigDecimal("0.00"), null, null));
        return orderStatMap;
    }

    private OrderStat getDanQuanWaterOrderAmount(QueryPara queryPara) {
        DataSourceContextHolder.setDbType(CommonConstant.fupinDataSource);
        List<OrderStat> danQuanWater = orderStatDao.getDanQuanWaterOrderAmount(queryPara);
        if (danQuanWater != null && danQuanWater.size() > 0) {
            return danQuanWater.get(0);
        } else {
            OrderStat orderStat = new OrderStat();
            orderStat.setOrderNum(0);
            orderStat.setOrderAmount(new BigDecimal("0.00"));
            return orderStat;
        }
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
        //只包含线上零售和线上集采的部分，因为线下邮件所用供应商因人工录入可能会导致问题，所以线下邮件部分直接取地区维度的
        DataSourceContextHolder.setDbType(CommonConstant.fupinDataSource);
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
        neiMengAmount.setArea("乌兰察布");
        neiMengAmount.setOrderNum(0);
        neiMengAmount.setOrderAmount(new BigDecimal("0.00"));
        Set<String> fupinSpecifiedAreaSet = this.getFupinSpecifiedArea();
        for (Map.Entry<String, OrderStat> entry : areaStatMap.entrySet()) {
            String key = entry.getKey();
            OrderStat value = entry.getValue();
            if ("丹江口".equals(key)) {
                value.setOrderAmount(value.getOrderAmount().subtract(new BigDecimal("720.2")));
            } else if ("龙州".equals(key)) {
                value.setOrderAmount(value.getOrderAmount().subtract(new BigDecimal("6836.42")));
            } else if ("郧西".equals(key)) {
                value.setOrderAmount(value.getOrderAmount().subtract(new BigDecimal("395.57")));
            }
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
        List<String> sellerIDList = orderStatDao.getFuPinSellerIDList();
        String sellerIDFilter = getSellerFilterUsingList(sellerIDList);
        queryPara.setWhereCondition(sellerIDFilter);
        DataSourceContextHolder.setDbType(CommonConstant.fupinDataSource);
        List<OrderStat> expressDisList = orderStatDao.getExpressDisList(queryPara);
        orderStatList.add(expressDisList);
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

    public List<OrderStat> getOrderExpressInfo(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<String> sellerIDList = orderStatDao.getFuPinSellerIDList();
        String sellerIDFilter = getSellerFilterUsingList(sellerIDList);
        queryPara.setWhereCondition(sellerIDFilter);
        DataSourceContextHolder.setDbType(CommonConstant.fupinDataSource);
        List<OrderStat> expressDisList = orderStatDao.getExpressDisList(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(expressDisList));
        return expressDisList;
    }
}
