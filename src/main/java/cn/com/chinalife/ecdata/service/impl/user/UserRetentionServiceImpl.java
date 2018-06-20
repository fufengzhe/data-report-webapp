package cn.com.chinalife.ecdata.service.impl.user;

import cn.com.chinalife.ecdata.dao.noSqlDao.user.UserRetentionNoSQLDao;
import cn.com.chinalife.ecdata.dao.sqlDao.InitDao;
import cn.com.chinalife.ecdata.dao.sqlDao.user.UserRetentionSQLDao;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.UserRetention;
import cn.com.chinalife.ecdata.entity.user.UserSource;
import cn.com.chinalife.ecdata.service.InitService;
import cn.com.chinalife.ecdata.service.user.UserRetentionService;
import cn.com.chinalife.ecdata.utils.CommonUtils;
import cn.com.chinalife.ecdata.utils.DateUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
@Service
public class UserRetentionServiceImpl implements UserRetentionService {
    private final Logger logger = LoggerFactory.getLogger(UserRetentionServiceImpl.class);
    @Autowired
    UserRetentionSQLDao userRetentionSQLDao;
    @Autowired
    UserRetentionNoSQLDao userRetentionNoSQLDao;
    @Autowired
    InitService initService;
    @Autowired
    InitDao initDao;


    public List<UserRetention> getUserRetentionList(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        queryPara.setUserSource(CommonUtils.getWhereConditionUsingPara(queryPara.getUserSource()));
        List<UserRetention> userRetentionList = userRetentionSQLDao.getUserRetentionList(queryPara);
        for (UserRetention userRetention : userRetentionList) {
            userRetention.setRetentionRate01(userRetention.getRetentionRate01() == null ? null :
                    CommonUtils.getPercentageStr(new BigDecimal(userRetention.getRetentionRate01()), BigDecimal.ROUND_HALF_UP));
            userRetention.setRetentionRate02(userRetention.getRetentionRate02() == null ? null :
                    CommonUtils.getPercentageStr(new BigDecimal(userRetention.getRetentionRate02()), BigDecimal.ROUND_HALF_UP));
            userRetention.setRetentionRate03(userRetention.getRetentionRate03() == null ? null :
                    CommonUtils.getPercentageStr(new BigDecimal(userRetention.getRetentionRate03()), BigDecimal.ROUND_HALF_UP));
            userRetention.setRetentionRate04(userRetention.getRetentionRate04() == null ? null :
                    CommonUtils.getPercentageStr(new BigDecimal(userRetention.getRetentionRate04()), BigDecimal.ROUND_HALF_UP));
            userRetention.setRetentionRate05(userRetention.getRetentionRate05() == null ? null :
                    CommonUtils.getPercentageStr(new BigDecimal(userRetention.getRetentionRate05()), BigDecimal.ROUND_HALF_UP));
            userRetention.setRetentionRate06(userRetention.getRetentionRate06() == null ? null :
                    CommonUtils.getPercentageStr(new BigDecimal(userRetention.getRetentionRate06()), BigDecimal.ROUND_HALF_UP));
            userRetention.setRetentionRate07(userRetention.getRetentionRate07() == null ? null :
                    CommonUtils.getPercentageStr(new BigDecimal(userRetention.getRetentionRate07()), BigDecimal.ROUND_HALF_UP));
            userRetention.setRetentionRate14(userRetention.getRetentionRate14() == null ? null :
                    CommonUtils.getPercentageStr(new BigDecimal(userRetention.getRetentionRate14()), BigDecimal.ROUND_HALF_UP));
            userRetention.setRetentionRate30(userRetention.getRetentionRate30() == null ? null :
                    CommonUtils.getPercentageStr(new BigDecimal(userRetention.getRetentionRate30()), BigDecimal.ROUND_HALF_UP));
        }
        logger.info("service返回结果为 {}", JSON.toJSONString(userRetentionList));
        return userRetentionList;
    }

    public int updateUserRetention(QueryPara queryPara) throws Exception {
        int effectedRowNum = 0;
        List<String> registerTimeToCalList = DateUtils.getDateList(DateUtils.getBeforeXDayBasedGivenDate(queryPara.getStartDate(), 30),
                DateUtils.getBeforeXDayBasedGivenDate(queryPara.getStartDate(), 1));
        int existedRetentionRowNum = userRetentionSQLDao.getExistedRetentionRowNum();
        int retentionOffset = 0;
        if (existedRetentionRowNum == 0) {
            retentionOffset = 29;
        }
        List<String> activeTimeToBaseList = DateUtils.getDateList(DateUtils.getBeforeXDayBasedGivenDate(queryPara.getStartDate(), retentionOffset),
                DateUtils.getBeforeXDayBasedGivenDate(queryPara.getStartDate(), 0));
        List<UserSource> userSourceList = initDao.getNewUserSource();
        Map<String, List<String>> dateUserSourceAndActiveOldUserIdListMap = getMapUsingDateList(activeTimeToBaseList, userSourceList);
        List<UserRetention> userRetentionList = new ArrayList<UserRetention>();
        for (String registerTime : registerTimeToCalList) {
            for (UserSource userSource : userSourceList) {
                QueryPara temp = new QueryPara();
                temp.setQueryDate(registerTime);
                temp.setUserSource(userSource.getUserSourceName());
                Integer registerNum = userRetentionSQLDao.getRegisterNumFromStatResult(temp);
                if (registerNum == null || registerNum == 0) {
                    for (String retentionTime : activeTimeToBaseList) {
                        if (registerTime.compareTo(retentionTime) < 0) {
                            UserRetention userRetention = new UserRetention();
                            userRetention.setRegisterTime(registerTime);
                            userRetention.setUserSource(userSource.getUserSource());
                            userRetention.setRetentionTime(retentionTime);
                            userRetention.setRetentionNum(0);
                            userRetentionList.add(userRetention);
                        }
                    }
                } else {
                    temp.setUserSource(userSource.getOldUserSource());//老渠道，因为从oracle中取数
                    List<String> registerOldUserIdList = userRetentionSQLDao.getOldUserIdList(temp);
                    for (String retentionTime : activeTimeToBaseList) {
                        if (registerTime.compareTo(retentionTime) < 0) {
                            List<String> activeUserIdList = dateUserSourceAndActiveOldUserIdListMap.get(retentionTime + "&" + userSource.getUserSource());
                            UserRetention userRetention = new UserRetention();
                            userRetention.setRegisterTime(registerTime);
                            userRetention.setUserSource(userSource.getUserSource());
                            userRetention.setRetentionTime(retentionTime);
                            if (activeUserIdList == null) {
                                userRetention.setRetentionNum(0);
                            } else {
                                List<String> tempRegisterUserIdList = new ArrayList<String>();
                                tempRegisterUserIdList.addAll(registerOldUserIdList);
                                tempRegisterUserIdList.retainAll(activeUserIdList);
                                userRetention.setRetentionNum(tempRegisterUserIdList.size());
                            }
                            userRetentionList.add(userRetention);
                        }
                    }
                }
            }
        }
        effectedRowNum = this.convertUserSourceToChnAndInsertToDB(userRetentionList);
        return effectedRowNum;
    }

    public List<UserRetention> getUserRetentionListForTrendChart(List<UserRetention> userRetentionList, boolean isUserSourceDimension) {
        List<UserRetention> userRetentionListToReturn = new ArrayList<UserRetention>();
        for (int i = 0; isUserSourceDimension ? i < userRetentionList.size() : (i < 7 && i < userRetentionList.size()); i++) {
            UserRetention userRetention = userRetentionList.get(i);
            UserRetention temp = new UserRetention();
            temp.setRegisterTime(userRetention.getRegisterTime());
            temp.setUserSource(userRetention.getUserSource());
            temp.setRetentionRate01(userRetention.getRetentionRate01() == null ? "0.00" : userRetention.getRetentionRate01().replaceAll("%", ""));
            temp.setRetentionRate02(userRetention.getRetentionRate02() == null ? "0.00" : userRetention.getRetentionRate02().replaceAll("%", ""));
            temp.setRetentionRate03(userRetention.getRetentionRate03() == null ? "0.00" : userRetention.getRetentionRate03().replaceAll("%", ""));
            temp.setRetentionRate04(userRetention.getRetentionRate04() == null ? "0.00" : userRetention.getRetentionRate04().replaceAll("%", ""));
            temp.setRetentionRate05(userRetention.getRetentionRate05() == null ? "0.00" : userRetention.getRetentionRate05().replaceAll("%", ""));
            temp.setRetentionRate06(userRetention.getRetentionRate06() == null ? "0.00" : userRetention.getRetentionRate06().replaceAll("%", ""));
            temp.setRetentionRate07(userRetention.getRetentionRate07() == null ? "0.00" : userRetention.getRetentionRate07().replaceAll("%", ""));
            temp.setRetentionRate14(userRetention.getRetentionRate14() == null ? "0.00" : userRetention.getRetentionRate14().replaceAll("%", ""));
            temp.setRetentionRate30(userRetention.getRetentionRate30() == null ? "0.00" : userRetention.getRetentionRate30().replaceAll("%", ""));
            userRetentionListToReturn.add(temp);
        }
        return userRetentionListToReturn;
    }

    public List<String> getxAxisLabel() {
        List<String> dateList = new ArrayList<String>();
        dateList.add("1天后");
        dateList.add("2天后");
        dateList.add("3天后");
        dateList.add("4天后");
        dateList.add("5天后");
        dateList.add("6天后");
        dateList.add("7天后");
        dateList.add("14天后");
        dateList.add("30天后");
        return dateList;
    }

    private int convertUserSourceToChnAndInsertToDB(List<UserRetention> userRetentionList) {
        if (userRetentionList != null && userRetentionList.size() > 0) {
            Map<String, String> map = initService.getNewUserSourceCodeAndName();
            for (UserRetention userRetention : userRetentionList) {
                String userSourceCode = userRetention.getUserSource();
                userRetention.setUserSource(map.get(userSourceCode) == null ? userSourceCode : map.get(userSourceCode));
            }
            return userRetentionSQLDao.insertListToDB(userRetentionList);
        } else {
            return 0;
        }
    }

    private Map<String, List<String>> getMapUsingDateList(List<String> activeTimeToBaseList, List<UserSource> userSourceList) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String date : activeTimeToBaseList) {
            for (UserSource userSource : userSourceList) {
                List<UserSource> temp = new ArrayList<UserSource>();
                temp.add(userSource);
                List<String> userIdList = userRetentionNoSQLDao.getActiveOldUserIdList(temp, date);
                map.put(date + "&" + userSource.getUserSource(), userIdList);
            }
        }
        return map;
    }
}
