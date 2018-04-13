package cn.com.chinalife.ecdata.service.impl.trade;

import cn.com.chinalife.ecdata.dao.sqlDao.trade.PropertyPremiumDao;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.trade.Branch;
import cn.com.chinalife.ecdata.entity.trade.Order;
import cn.com.chinalife.ecdata.entity.trade.Premium;
import cn.com.chinalife.ecdata.service.trade.PropertyPremiumService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.CommonUtils;
import cn.com.chinalife.ecdata.utils.DataSourceContextHolder;
import cn.com.chinalife.ecdata.utils.DateUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
@Service
public class PropertyPremiumServiceImpl implements PropertyPremiumService {
    private final Logger logger = LoggerFactory.getLogger(PropertyPremiumServiceImpl.class);
    @Autowired
    PropertyPremiumDao propertyPremiumDao;

    public List<Premium> getPropertyPremiumOverview() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<Premium> premiumList = propertyPremiumDao.getPropertyPremiumOverview(CommonConstant.statIndexNameListOfPropertyPremium);
        //TODO 计算日环比，月环比，注意按照每个渠道进行循环，各个电销中心，网销，总计
        String[] statIndex = new String[]{"湖南长沙中心", "肇庆电销中心", "上海电销中心", "网销", "总计"};
        Map<String, Premium> dateAndPremiumMap = new HashMap<String, Premium>();
        for (Premium premium : premiumList) {
            dateAndPremiumMap.put(premium.getStatDay() + "&" + premium.getBranchName(), premium);
        }
        List<Premium> premiumListToReturn = new ArrayList<Premium>();
        for (String index : statIndex) {
            String yesterday = DateUtils.getYesterday();
            Premium premium = dateAndPremiumMap.get(yesterday + "&" + index);
            if (premium == null) {
                premium.setStatDay(yesterday);
                premium.setBranchName(index);
                premium.setDayAmount(new BigDecimal("0.00"));
                premium.setLastDayAmount(new BigDecimal("0.00"));
                premium.setMonthAmount(new BigDecimal("0.00"));
                premium.setLastMonthAmount(new BigDecimal("0.00"));
                premium.setYearAmount(new BigDecimal("0.00"));
            }
            premium.setDayRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(premium.getDayAmount().subtract(premium.getLastDayAmount()), premium.getLastDayAmount(), 4)));
            premium.setMonthRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(premium.getMonthAmount().subtract(premium.getLastMonthAmount()), premium.getLastMonthAmount(), 4)));
            if ("总计".equals(index)) {
                premium.setCompleteRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(premium.getYearAmount(), new BigDecimal("4000000000"), 4)));
            }
            premiumListToReturn.add(premium);
        }
        CommonUtils.convertPremium(premiumListToReturn);
        logger.info("service返回结果为 {}", JSON.toJSONString(premiumListToReturn));
        return premiumListToReturn;
    }

    public List<Premium> getPropertyPremiumDetail(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        List<Premium> premiumList = propertyPremiumDao.getPropertyPremiumDetail(queryPara);
        for (Premium premium : premiumList) {
            premium.setAccumulatedAmount(CommonUtils.convertToTenThousandUnit(premium.getAccumulatedAmount()));
        }
        logger.info("service返回结果为 {}", JSON.toJSONString(premiumList));
        return premiumList;
    }

    public int deleteAllExistedRecord(List<String> statIndexNameListOfPropertyPremium) {
        return propertyPremiumDao.deleteAllExistedRecord(statIndexNameListOfPropertyPremium);
    }

    public int updatePropertyPremium(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        int effectedRow = 0;
        int temp;
        //计算财险电销商业险
        List<Premium> premiumListOfSY = propertyPremiumDao.getPremiumDetailListOfSY(queryPara);
        logger.info("商业险查询结果为 {}", JSON.toJSONString(premiumListOfSY));
        temp = propertyPremiumDao.updatePropertyPremium(premiumListOfSY);
        effectedRow += temp;
        //计算财险电销交强险
        List<Premium> premiumListOfJQ = propertyPremiumDao.getPremiumDetailListOfJQ(queryPara);
        logger.info("交强险查询结果为 {}", JSON.toJSONString(premiumListOfJQ));
        temp = propertyPremiumDao.updatePropertyPremium(premiumListOfJQ);
        effectedRow += temp;
        //计算财险电销批退批改
        List<Premium> premiumListOfPTPG = propertyPremiumDao.getPremiumDetailListOfPTPG(queryPara);
        logger.info("批退批改查询结果为 {}", JSON.toJSONString(premiumListOfPTPG));
        temp = propertyPremiumDao.updatePropertyPremium(premiumListOfPTPG);
        effectedRow += temp;
        //计算财险网销
        List<Premium> premiumListOfInternet = propertyPremiumDao.getPremiumDetailListOfInternet(queryPara);
        logger.info("网销查询结果为 {}", JSON.toJSONString(premiumListOfInternet));
        temp = propertyPremiumDao.updatePropertyPremium(premiumListOfInternet);
        effectedRow += temp;
        return effectedRow;
    }

    private void handleReverseAndCorrect(List<Order> orderList, List<Order> reverseAndCorrectOrderList) {
        Map<String, BigDecimal> reverseAndCorrectMap = new HashMap<String, BigDecimal>();
        for (Order order : reverseAndCorrectOrderList) {
            reverseAndCorrectMap.put(order.getPolicyNo(), order.getPremium());
        }
        Set<String> policySet = reverseAndCorrectMap.keySet();
        for (Order order : orderList) {
            String policyNoJQ = order.getPolicyNoJQ();
            String policyNoSY = order.getPolicyNoSY();
            boolean jqContains = (policyNoJQ != null && policySet.contains(policyNoJQ));
            boolean syContains = (policyNoSY != null && policySet.contains(policyNoSY));
            BigDecimal chgPremium = new BigDecimal("0");
            if (jqContains) {
                chgPremium = reverseAndCorrectMap.get(policyNoJQ);
            }
            if (syContains) {
                chgPremium = reverseAndCorrectMap.get(policyNoSY);
            }
            if (jqContains || syContains) {
                order.setPremium(order.getPremium().add(chgPremium));
            }
        }
    }

    private List<Premium> groupListByDeptNo(List<Order> orderList, List<Branch> branchList) {
        Map<String, String> branchNoAndNameMap = new HashMap<String, String>();
        for (Branch branch : branchList) {
            branchNoAndNameMap.put(branch.getBranchNo(), branch.getBranchName());
        }
        Map<String, BigDecimal> branchNameAndPremiumMap = new HashMap<String, BigDecimal>();
        for (Order order : orderList) {
            String deptNo = order.getDepartNo();
            BigDecimal premium = order.getPremium();
            boolean isBeginWithGivenStr = deptNo.startsWith("2102") || deptNo.startsWith("3302") || deptNo.startsWith("3502") || deptNo.startsWith("3702") || deptNo.startsWith("4403");
            if (isBeginWithGivenStr) {
                deptNo = deptNo.substring(0, 4);
            } else {
                deptNo = deptNo.substring(0, 2);
            }
            String branchName = (branchNoAndNameMap.get(deptNo) == null ? "未知" : branchNoAndNameMap.get(deptNo));
            BigDecimal accumulatedPremium = branchNameAndPremiumMap.get(branchName);
            if (accumulatedPremium == null) {
                accumulatedPremium = new BigDecimal("0");
            } else {
                accumulatedPremium = accumulatedPremium.add(premium);
            }
            branchNameAndPremiumMap.put(branchName, accumulatedPremium);
        }
        return CommonUtils.getPremiumListUsingMap(branchNameAndPremiumMap);
    }
}