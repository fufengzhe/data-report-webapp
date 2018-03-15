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

    public Premium getPremiumOverview() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        Premium premium = propertyPremiumDao.getPremiumOverview();
        premium.setDayRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(premium.getDayAmount().subtract(premium.getLastDayAmount()), premium.getLastDayAmount(), 4)));
        premium.setMonthRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(premium.getMonthAmount().subtract(premium.getLastMonthAmount()), premium.getLastMonthAmount(), 4)));
        premium.setCompleteRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(premium.getYearAmount(), premium.getYearGoal(), 4)));
        logger.info("service返回结果为 {}", JSON.toJSONString(premium));
        return premium;
    }

    public List<Premium> getPremiumDetail(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        //计算批退和批改之前的明细
        List<Order> orderList = propertyPremiumDao.getPremiumDetailWithoutReverseAndCorrect(queryPara);
        //获取批退和批改列表
        List<Order> reverseAndCorrectOrderList = propertyPremiumDao.getReverseAndCorrectOrderList(queryPara);
        this.handleReverseAndCorrect(orderList, reverseAndCorrectOrderList);
        List<Branch> branchList = propertyPremiumDao.getBranchList();
        List<Premium> premiumList = this.groupListByDeptNo(orderList, branchList);
        logger.info("service返回结果为 {}", JSON.toJSONString(premiumList));
        return premiumList;
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
        List<Premium> premiumList = new ArrayList<Premium>();
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
        for (Map.Entry<String, BigDecimal> entry : branchNameAndPremiumMap.entrySet()) {
            Premium premium = new Premium();
            premium.setBranchName(entry.getKey());
            premium.setAccumulatedAmount(CommonUtils.divideWithXPrecision(entry.getValue(), new BigDecimal("10000"), 2)); //换算成万
            premiumList.add(premium);
        }
        Collections.sort(premiumList, new Comparator<Premium>() {
            public int compare(Premium o1, Premium o2) {
                return o2.getAccumulatedAmount().subtract(o1.getAccumulatedAmount()).compareTo(new BigDecimal("0"));
            }
        });
        return premiumList;
    }
}
