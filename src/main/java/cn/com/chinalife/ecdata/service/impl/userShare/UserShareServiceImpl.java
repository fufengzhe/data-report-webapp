package cn.com.chinalife.ecdata.service.impl.userShare;

import cn.com.chinalife.ecdata.dao.sqlDao.userShare.UserShareDao;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.userShare.UserShare;
import cn.com.chinalife.ecdata.service.InitService;
import cn.com.chinalife.ecdata.service.userShare.UserShareService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.CommonUtils;
import cn.com.chinalife.ecdata.utils.DateUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
@Service
public class UserShareServiceImpl implements UserShareService {
    private final Logger logger = LoggerFactory.getLogger(UserShareServiceImpl.class);
    @Autowired
    UserShareDao userShareDao;
    @Autowired
    InitService initService;

    public int updateUserShare(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        Map<String, String> codeAndName = initService.getAllNewUserSourceCodeAndName();
        int effectedRow = 0;
        List<UserShare> userShareList = userShareDao.getUserShareDisInfo(queryPara);
        for (UserShare userShare : userShareList) {
            userShare.setStatTimeSpan(CommonConstant.statTimeSpanOfDate);
            userShare.setIndexName(CommonConstant.statIndexNameOfUserShare);
            userShare.setDistributeType("8");
            userShare.setUserSource(codeAndName.get(userShare.getUserSource()) == null ? userShare.getUserSource() : codeAndName.get(userShare.getUserSource()));
        }
        if (userShareList != null && userShareList.size() > 0) {
            effectedRow = userShareDao.updateUserShare(userShareList);
        }
        logger.info("service更新完成，受影响行数为 {}", effectedRow);
        return effectedRow;
    }

    public List<UserShare> getUserSourceList() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        List<UserShare> userSourceList = userShareDao.getUserSourceList();
        logger.info("service返回结果为 {}", JSON.toJSONString(userSourceList));
        return userSourceList;
    }

    public List<UserShare> getUserSourceDisList(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        queryPara.setUserSource(CommonUtils.getWhereConditionUsingPara(queryPara.getUserSource()));
        List<UserShare> userSourceDisList = userShareDao.getUserSourceDisList(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(userSourceDisList));
        return userSourceDisList;
    }

    public List<UserShare> getHourDisList(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
//        queryPara.setUserSource(CommonUtils.getWhereConditionUsingPara(queryPara.getUserSource()));
        List<UserShare> hourDisList = userShareDao.getHourDisList(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(hourDisList));
        return hourDisList;
    }

    public List<UserShare> getDateTrend(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        queryPara.setStartDate(DateUtils.getBeforeXDay(7));
        queryPara.setEndDate(DateUtils.getBeforeXDay(1));
        List<UserShare> dateTrendList = userShareDao.getDateTrendList(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(dateTrendList));
        return dateTrendList;
    }
}
