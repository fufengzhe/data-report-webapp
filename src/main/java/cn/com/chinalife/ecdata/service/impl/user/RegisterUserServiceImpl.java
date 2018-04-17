package cn.com.chinalife.ecdata.service.impl.user;

import cn.com.chinalife.ecdata.dao.sqlDao.InitDao;
import cn.com.chinalife.ecdata.dao.sqlDao.user.RegisterUserDao;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.RegisterUser;
import cn.com.chinalife.ecdata.entity.user.UserSource;
import cn.com.chinalife.ecdata.service.user.RegisterUserService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.CommonUtils;
import cn.com.chinalife.ecdata.utils.DataSourceContextHolder;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
@Service
public class RegisterUserServiceImpl implements RegisterUserService {
    private final Logger logger = LoggerFactory.getLogger(RegisterUserServiceImpl.class);
    @Autowired
    RegisterUserDao registerUserDao;

    @Autowired
    InitDao initDao;


    public RegisterUser getRegisterUserNum(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        RegisterUser registerUser = registerUserDao.getRegisterUserNum(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(registerUser));
        return registerUser;
    }

    public List<RegisterUser> getRegisterNumOverview() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<RegisterUser> registerUserList = registerUserDao.getRegisterNumOverview();
        for (RegisterUser registerUser : registerUserList) {
            registerUser.setDayRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(registerUser.getDayNum().subtract(registerUser.getLastDayNum()), registerUser.getLastDayNum(), 4)));
            registerUser.setMonthRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(registerUser.getMonthNum().subtract(registerUser.getLastMonthNum()), registerUser.getLastMonthNum(), 4)));
            registerUser.setCompleteRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(registerUser.getYearNum(), registerUser.getYearGoal(), 4)));
        }
        logger.info("service返回结果为 {}", JSON.toJSONString(registerUserList));
        return registerUserList;
    }

    public List<RegisterUser> getRegisterUserNumOfAllSources(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        List<UserSource> userSourceList = initDao.getOldUserSource();
        String userSourceCondition = getUserSourceConditionUsingList(userSourceList);
        queryPara.setWhereCondition(userSourceCondition);
        List<RegisterUser> registerUserList = registerUserDao.getRegisterUserNumOfAllSources(queryPara);
        Map<String, String> userSourceCodeAndNameMap = new HashMap<String, String>();
        for (UserSource userSource : userSourceList) {
            userSourceCodeAndNameMap.put(userSource.getUserSource(), userSource.getUserSourceName());
        }
        userSourceCodeAndNameMap.put("21", "e宝APP");
        userSourceCodeAndNameMap.put("22", "e宝微信");
        userSourceCodeAndNameMap.put("23", "e宝柜面");
        for (RegisterUser registerUser : registerUserList) {
            registerUser.setTimeSpan(CommonConstant.statTimeSpanOfDate);
            registerUser.setIndexName(CommonConstant.statIndexNameOfRegister);
            registerUser.setUserSource(userSourceCodeAndNameMap.get(registerUser.getUserSource()) == null ? "未知" : userSourceCodeAndNameMap.get(registerUser.getUserSource().trim()));
        }
        Collections.sort(registerUserList, new Comparator<RegisterUser>() {
            public int compare(RegisterUser o1, RegisterUser o2) {
                return o2.getRegisterUserNum() - o1.getRegisterUserNum();
            }
        });
        logger.info("service返回结果为 {}", JSON.toJSONString(registerUserList));
        return registerUserList;
    }

    public int updateRegister(List<RegisterUser> registerUserList) {
        return registerUserDao.updateRegister(registerUserList);
    }

    public List<RegisterUser> getRegisterUserNumOfAllSourcesFromStatResult(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        List<RegisterUser> registerUserList = registerUserDao.getRegisterUserNumOfAllSourcesFromStatResult(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(registerUserList));
        return registerUserList;
    }

    private String getUserSourceConditionUsingList(List<UserSource> userSourceList) {
        StringBuilder userSourceCondition = new StringBuilder();
        if (userSourceList.size() > 0) {
            userSourceCondition.append(" USERSOURCE IN (");
            for (int i = 0; i < userSourceList.size() - 1; i++) {
                userSourceCondition.append("'").append(userSourceList.get(i).getUserSource()).append("',");
            }
            userSourceCondition.append("'").append(userSourceList.get(userSourceList.size() - 1).getUserSource()).append("','21','22','23') AND ");
        }
        return userSourceCondition.toString();
    }
}
