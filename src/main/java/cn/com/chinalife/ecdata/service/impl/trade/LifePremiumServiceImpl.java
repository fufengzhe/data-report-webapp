package cn.com.chinalife.ecdata.service.impl.trade;

import cn.com.chinalife.ecdata.dao.sqlDao.trade.LifePremiumDao;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.trade.Order;
import cn.com.chinalife.ecdata.entity.trade.Premium;
import cn.com.chinalife.ecdata.service.trade.LifePremiumService;
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
import java.text.ParseException;
import java.util.*;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
@Service
public class LifePremiumServiceImpl implements LifePremiumService {
    private final Logger logger = LoggerFactory.getLogger(LifePremiumServiceImpl.class);
    @Autowired
    LifePremiumDao lifePremiumDao;

    public List<Premium> getLifePremiumOverview() throws ParseException {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<Premium> premiumList = lifePremiumDao.getLifePremiumOverview(CommonConstant.statIndexNameOfLifePremium);
        //TODO 计算日环比，月环比，注意按照每个渠道进行循环，各个电销中心，网销，总计
        String[] statIndex = new String[]{"郑州电销中心", "合肥电销中心", "成都电销中心", "网销", "总计"};
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
                CommonUtils.setDefaultForPremium(premium);
            }
            premium.setDayRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(premium.getDayAmount().subtract(premium.getLastDayAmount()), premium.getLastDayAmount(), 4)));
            premium.setMonthRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(premium.getMonthAmount().subtract(premium.getLastMonthAmount()), premium.getLastMonthAmount(), 4)));
            if ("总计".equals(index)) {
                premium.setCompleteRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(premium.getYearAmount(), new BigDecimal("280000000"), 4)));
            }
            premiumListToReturn.add(premium);
        }
        CommonUtils.convertPremium(premiumListToReturn);
        logger.info("service返回结果为 {}", JSON.toJSONString(premiumListToReturn));
        return premiumListToReturn;
    }

    private List<Premium> getPremiumListUsingUpperAndLower(List<Premium> premiumListOfUpper, List<Premium> premiumListOfLower) throws ParseException {
        List<Premium> premiumList = new ArrayList<Premium>();
        Map<String, Premium> mapOfUpper = new HashMap<String, Premium>();
        Map<String, Premium> mapOfLower = new HashMap<String, Premium>();
        for (Premium premium : premiumListOfUpper) {
            mapOfUpper.put(premium.getStatDay() + "&" + premium.getTimeSpan() + "&" + premium.getBranchName(), premium);
        }
        for (Premium premium : premiumListOfLower) {
            mapOfLower.put(premium.getStatDay() + "&" + premium.getTimeSpan() + "&" + premium.getBranchName(), premium);
        }
        //TODO 计算日环比，月环比，注意按照每个渠道进行循环，各个电销中心，网销，总计
        String[] statIndex = new String[]{"合肥电销中心", "成都电销中心", "郑州电销中心", "网销", "总计"};
        for (String index : statIndex) {
            Premium premium = new Premium();
            premium.setBranchName(index);
            premium.setDayAmount(mapOfUpper.get(DateUtils.getYesterday() + "&D&" + index) == null ?
                    new BigDecimal("0") : mapOfUpper.get(DateUtils.getYesterday() + "&D&" + index).getAccumulatedAmount());
            premium.setLastDayAmount(mapOfLower.get(DateUtils.getTheDayBeforeYesterday() + "&D&" + index) == null ?
                    new BigDecimal("0") : mapOfLower.get(DateUtils.getTheDayBeforeYesterday() + "&D&" + index).getAccumulatedAmount());
            premium.setMonthAmount(mapOfUpper.get(DateUtils.getMonthBeginDateUsingYesterday(DateUtils.getYesterday()) + "&M&" + index) == null ?
                    new BigDecimal("0") : mapOfUpper.get(DateUtils.getMonthBeginDateUsingYesterday(DateUtils.getYesterday()) + "&M&" + index).getAccumulatedAmount());
            premium.setLastMonthAmount(mapOfLower.get(DateUtils.getLastMonthBeginDateUsingYesterday(DateUtils.getYesterday()) + "&M&" + index) == null ?
                    new BigDecimal("0") : mapOfLower.get(DateUtils.getLastMonthBeginDateUsingYesterday(DateUtils.getYesterday()) + "&M&" + index).getAccumulatedAmount());
            premium.setYearAmount(mapOfUpper.get(DateUtils.getYearBeginDateUsingYesterday(DateUtils.getYesterday()) + "&Y&" + index) == null ?
                    new BigDecimal("0") : mapOfUpper.get(DateUtils.getYearBeginDateUsingYesterday(DateUtils.getYesterday()) + "&Y&" + index).getAccumulatedAmount());
            premium.setDayRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(premium.getDayAmount().subtract(premium.getLastDayAmount()), premium.getLastDayAmount(), 4)));
            premium.setMonthRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(premium.getMonthAmount().subtract(premium.getLastMonthAmount()), premium.getLastMonthAmount(), 4)));
            if ("总计".equals(index)) {
                premium.setYearGoal(new BigDecimal("280000000"));
                premium.setCompleteRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(premium.getYearAmount(), premium.getYearGoal(), 4)));
            }
            premiumList.add(premium);
        }
        return premiumList;
    }

    public List<Premium> getLifePremiumDetail(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<Premium> premiumList = lifePremiumDao.getLifePremiumDetail(queryPara);
        List<Premium> premiumListOfInternet = lifePremiumDao.getLifePremiumDetailOfInternet(queryPara);
//        List<Premium> premiumListOfAll = lifePremiumDao.getLifePremiumDetailWithoutDistinctBranch(queryPara);
        premiumList.addAll(premiumListOfInternet);
        for (Premium premium : premiumList) {
            premium.setIndexName(CommonConstant.statIndexNameOfLifePremium);
        }
        logger.info("service返回结果为 {}", JSON.toJSONString(premiumList));
        return premiumList;
    }

    public int updateLifePremium(List<Premium> premiumList) {
        if (premiumList != null && premiumList.size() > 0) {
            return lifePremiumDao.updateLifePremium(premiumList);
        } else {
            return 0;
        }
    }

    public int deleteAllExistedRecord(String indexName) {
        return lifePremiumDao.deleteAllExistedRecord(indexName);
    }

    public List<Premium> getLifePremiumDetailFromStatResult(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<Premium> premiumList = lifePremiumDao.getLifePremiumDetailFromStatResult(queryPara);
        for (Premium premium : premiumList) {
            premium.setAccumulatedAmount(CommonUtils.convertToTenThousandUnit(premium.getAccumulatedAmount()));
        }
        logger.info("service返回结果为 {}", JSON.toJSONString(premiumList));
        return premiumList;
    }

    public List<List<Premium>> getPreimumSummaryList(QueryPara queryPara, List<String> dateList) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<List<Premium>> premiumList = new ArrayList<List<Premium>>();
        List<Premium> premiumDisInfo = lifePremiumDao.getLifePremiumDisInfoList(queryPara);
        for (Premium premium : premiumDisInfo) {
            premium.setAccumulatedAmount(CommonUtils.convertToTenThousandUnit(premium.getAccumulatedAmount()));
        }
        premiumList.add(premiumDisInfo);
        List<Premium> premiumCompleteRatioInfo = lifePremiumDao.getLifePremiumCompleteRatioInfo(queryPara);
        premiumCompleteRatioInfo.get(0).setCompleteRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(premiumCompleteRatioInfo.get(0).getYearAmount(), new BigDecimal("280000000"), 4)));
        premiumCompleteRatioInfo.get(0).setYearAmount(CommonUtils.convertToTenThousandUnit(premiumCompleteRatioInfo.get(0).getYearAmount()));
        premiumList.add(premiumCompleteRatioInfo);
        queryPara.setStartDate(dateList.get(0));
        queryPara.setEndDate(dateList.get(dateList.size() - 1));
        List<Premium> premiumDateTrendInfo = lifePremiumDao.getLifeDateTrendInfo(queryPara);
        CommonUtils.convertToTenThousandUnitForPremium(premiumDateTrendInfo);
        premiumList.add(premiumDateTrendInfo);
        logger.info("service返回结果为 {}", JSON.toJSONString(premiumList));
        return premiumList;
    }

    public List<Premium> queryPremiumNum(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<Premium> premiumList = lifePremiumDao.getLifePremiumDisInfoList(queryPara);
        for (Premium premium : premiumList) {
            premium.setAccumulatedAmount(CommonUtils.convertToTenThousandUnit(premium.getAccumulatedAmount()));
        }
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
