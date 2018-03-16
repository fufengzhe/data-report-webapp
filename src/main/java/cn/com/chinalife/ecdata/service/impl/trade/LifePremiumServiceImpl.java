package cn.com.chinalife.ecdata.service.impl.trade;

import cn.com.chinalife.ecdata.dao.sqlDao.trade.LifePremiumDao;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.trade.Order;
import cn.com.chinalife.ecdata.entity.trade.Premium;
import cn.com.chinalife.ecdata.service.trade.LifePremiumService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.CommonUtils;
import cn.com.chinalife.ecdata.utils.DataSourceContextHolder;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
@Service
public class LifePremiumServiceImpl implements LifePremiumService {
    private final Logger logger = LoggerFactory.getLogger(LifePremiumServiceImpl.class);
    @Autowired
    LifePremiumDao lifePremiumDao;

    public Premium getLifePremiumOverview() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        Premium premium = lifePremiumDao.getLifePremiumOverview();
        premium.setDayRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(premium.getDayAmount().subtract(premium.getLastDayAmount()), premium.getLastDayAmount(), 4)));
        premium.setMonthRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(premium.getMonthAmount().subtract(premium.getLastMonthAmount()), premium.getLastMonthAmount(), 4)));
        premium.setCompleteRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(premium.getYearAmount(), premium.getYearGoal(), 4)));
        logger.info("service返回结果为 {}", JSON.toJSONString(premium));
        return premium;
    }

    public List<Premium> getLifePremiumDetail(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<Order> agentIDAndPolicyNoList = lifePremiumDao.getPremiumDetailWithOnlyAgentAndPolicyNo(queryPara);
        String policyNoFilterStr = this.getPolicyNoFilterStrUsingList(agentIDAndPolicyNoList);
        List<Order> policyNoAndPremiumList = lifePremiumDao.getPremiumDetailWithOnlyPolicyNoAndPremium(policyNoFilterStr);
        List<Premium> premiumList = this.getPremiumListUsingLists(agentIDAndPolicyNoList, policyNoAndPremiumList);
        logger.info("service返回结果为 {}", JSON.toJSONString(premiumList));
        return premiumList;
    }


    private String getPolicyNoFilterStrUsingList(List<Order> agentIDAndPolicyNoList) {
        StringBuilder sb = new StringBuilder(" ( ");
        int count = 1;
        int loop = 1;
        for (Order order : agentIDAndPolicyNoList) {
            if (count == 1) {
                sb.append(loop == 1 ? " CNTR_NO in ( " : " OR CNTR_NO in ( ");
                sb.append("'").append(order.getPolicyNo()).append("',");
                count++;
            } else if (count < 999) {
                sb.append("'").append(order.getPolicyNo()).append("',");
                count++;
            } else {
                sb.append("'").append(order.getPolicyNo()).append("')");
                count = 1;
                loop++;
            }
        }
        String result = sb.toString();
        if (sb.toString().endsWith(",")) {
            result = sb.substring(0, sb.length() - 1) + ")";
        }
        result = result + ")";
        return result;
    }

    private List<Premium> getPremiumListUsingLists(List<Order> agentIDAndPolicyNoList, List<Order> policyNoAndPremiumList) {
        Map<String, BigDecimal> policyNoAndPremiumMap = new HashMap<String, BigDecimal>();
        Map<String, BigDecimal> branchNameAndPremiumMap = new HashMap<String, BigDecimal>();
        for (Order order : policyNoAndPremiumList) {
            policyNoAndPremiumMap.put(order.getPolicyNo(), order.getPremium());
        }
        for (Order order : agentIDAndPolicyNoList) {
            String agentId = order.getAgentId();
            BigDecimal premium = policyNoAndPremiumMap.get(order.getPolicyNo());
            String branchName = "未知";
            if (agentId.startsWith("C")) {
                branchName = "成都电销中心";
            } else if (agentId.startsWith("H")) {
                branchName = "合肥电销中心";
            } else if (agentId.startsWith("Z")) {
                branchName = "郑州电销中心";
            }
            BigDecimal accumulatedPremium = branchNameAndPremiumMap.get(branchName);
            if (accumulatedPremium == null) {
                accumulatedPremium = new BigDecimal("0");
            } else {
                accumulatedPremium = accumulatedPremium.add(premium == null ? new BigDecimal("0") : premium);
            }
            branchNameAndPremiumMap.put(branchName, accumulatedPremium);
        }
        return CommonUtils.getPremiumListUsingMap(branchNameAndPremiumMap);
    }
}
