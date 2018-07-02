package cn.com.chinalife.ecdata.service.impl.user;

import cn.com.chinalife.ecdata.dao.sqlDao.InitDao;
import cn.com.chinalife.ecdata.dao.sqlDao.user.UserAttributeSQLDao;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.UserAttribute;
import cn.com.chinalife.ecdata.service.InitService;
import cn.com.chinalife.ecdata.service.user.UserAttributeService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.CommonUtils;
import cn.com.chinalife.ecdata.utils.DateUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
@Service
public class UserAttributeServiceImpl implements UserAttributeService {
    private final Logger logger = LoggerFactory.getLogger(UserAttributeServiceImpl.class);
    @Autowired
    UserAttributeSQLDao userAttributeSQLDao;
    @Autowired
    InitService initService;
    @Autowired
    InitDao initDao;

    public int updateUserAttribute(QueryPara queryPara, boolean isScheduledRun) throws ParseException {
        int effectedRowNum = 0;
        logger.info("开始更新注册用户年龄及等级分布,startDate {}, endDate {}", queryPara.getStartDate(), queryPara.getEndDate());
        this.deleteAllExistedRecord(CommonConstant.statIndexNameOfUserAge);
        this.deleteAllExistedRecord(CommonConstant.statIndexNameOfUserRank);
        int daySpan = DateUtils.getDaySpanBetweenStartAndEnd(queryPara.getStartDate(), queryPara.getEndDate()) + 1;
        int loopTimes = daySpan % 10 == 0 ? (daySpan / 10) : (daySpan / 10 + 1);
        Map<String, String> oldUserSourceMap = initService.getAllOldUserSourceCodeAndName();
        String startDate = queryPara.getStartDate();
        for (int i = 1; i <= loopTimes; i++) {
            QueryPara temp = new QueryPara();
            temp.setStartDate(startDate);
            temp.setEndDate(i == loopTimes ? queryPara.getEndDate() : (DateUtils.addXDateBasedGivenDate(startDate, 9)));
            logger.info("更新注册用户年龄及等级分布第{}轮,startDate {}, endDate {}", i, temp.getStartDate(), temp.getEndDate());
            List<UserAttribute> ageDisList = userAttributeSQLDao.getUserAgeDisInfoList(temp);
            for (UserAttribute userAttribute : ageDisList) {
                userAttribute.setIndexSource(oldUserSourceMap.get(userAttribute.getIndexSource()) == null ? userAttribute.getIndexSource() : oldUserSourceMap.get(userAttribute.getIndexSource()));
            }
            effectedRowNum += this.insertListToDB(ageDisList);
            List<UserAttribute> userRankList = userAttributeSQLDao.getUserRankDisInfoList(temp);
            for (UserAttribute userAttribute : userRankList) {
                userAttribute.setIndexSource(oldUserSourceMap.get(userAttribute.getIndexSource()) == null ? userAttribute.getIndexSource() : oldUserSourceMap.get(userAttribute.getIndexSource()));
            }
            effectedRowNum += this.insertListToDB(userRankList);
            startDate = DateUtils.addXDateBasedGivenDate(temp.getEndDate(), 1);
        }
        logger.info("完成更新注册用户年龄及等级分布");
        logger.info("开始更新注册用户性别分布");
        if (isScheduledRun) {
            queryPara.setStartDate(DateUtils.getYesterday());
        }
        List<UserAttribute> userSexList = userAttributeSQLDao.getUserSexDisInfoList(queryPara);
        for (UserAttribute userAttribute : userSexList) {
            userAttribute.setIndexSource(oldUserSourceMap.get(userAttribute.getIndexSource()) == null ? userAttribute.getIndexSource() : oldUserSourceMap.get(userAttribute.getIndexSource()));
        }
        effectedRowNum += this.insertListToDB(userSexList);
        logger.info("完成更新注册用户性别分布");
        return effectedRowNum;
    }

    public int deleteAllExistedRecord(String distributeName) {
        return userAttributeSQLDao.deleteAllExistedRecord(distributeName);
    }

    public int insertListToDB(List<UserAttribute> userAttributeList) {
        if (userAttributeList != null && userAttributeList.size() > 0) {
            return userAttributeSQLDao.insertListToDB(userAttributeList);
        } else {
            return 0;
        }
    }

    public List<UserAttribute> getUserSexList(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        queryPara.setUserSource(CommonUtils.getWhereConditionUsingPara(queryPara.getUserSource()));
        List<UserAttribute> sexList = userAttributeSQLDao.getUserSexList(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(sexList));
        return sexList;
    }

    public List<UserAttribute> getUserRankList(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
//        queryPara.setUserSource(CommonUtils.getWhereConditionUsingPara(queryPara.getUserSource()));
        List<UserAttribute> rankList = userAttributeSQLDao.getUserRankList(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(rankList));
        return rankList;
    }

    public List<UserAttribute> getUserAgeList(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
//        queryPara.setUserSource(CommonUtils.getWhereConditionUsingPara(queryPara.getUserSource()));
        List<UserAttribute> ageList = userAttributeSQLDao.getUserAgeList(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(ageList));
        return ageList;
    }
}
