package cn.com.chinalife.ecdata.service.impl.user;

import cn.com.chinalife.ecdata.dao.noSqlDao.user.ActiveUserDao;
import cn.com.chinalife.ecdata.dao.sqlDao.InitDao;
import cn.com.chinalife.ecdata.dao.sqlDao.user.ActiveUserSQLDao;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.ActiveUser;
import cn.com.chinalife.ecdata.entity.user.UserSource;
import cn.com.chinalife.ecdata.service.user.ActiveUserService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
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
public class ActiveUserServiceImpl implements ActiveUserService {
    private final Logger logger = LoggerFactory.getLogger(ActiveUserServiceImpl.class);
    @Autowired
    ActiveUserDao activeUserDao;
    @Autowired
    ActiveUserSQLDao activeUserSQLDao;
    @Autowired
    InitDao initDao;

    public ActiveUser getActiveUserNum(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        ActiveUser activeUser = activeUserDao.getActiveUserNum(queryPara);
        activeUser.setUserSource(queryPara.getUserSource());
        activeUser.setStartDate(queryPara.getStartDate());
        activeUser.setEndDate(queryPara.getEndDate());
        logger.info("service返回结果为 {}", JSON.toJSONString(activeUser));
        return activeUser;
    }

    public List<ActiveUser> getActiveUserNumOfAllSources(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        List<ActiveUser> activeUserList = activeUserDao.getActiveUserNumOfAllSources(queryPara);
        List<UserSource> userSourceList = initDao.getNewUserSource();
        Map<String, String> userSourceCodeAndNameMap = new HashMap<String, String>();
        for (UserSource userSource : userSourceList) {
            userSourceCodeAndNameMap.put(userSource.getUserSource(), userSource.getUserSourceName());
        }
        for (ActiveUser activeUser : activeUserList) {
            if (activeUser.getStartDate() == null) {
                activeUser.setStartDate(queryPara.getStartDate());
            }
            activeUser.setTimeSpan(queryPara.getTimeSpan());
            activeUser.setIndexName(CommonConstant.statIndexNameOfActive);
            activeUser.setUserSource(userSourceCodeAndNameMap.get(activeUser.getUserSource()) == null ? "未知" : userSourceCodeAndNameMap.get(activeUser.getUserSource()));
        }
        Collections.sort(activeUserList, new Comparator<ActiveUser>() {
            public int compare(ActiveUser o1, ActiveUser o2) {
                return o2.getActiveUserNum() - o1.getActiveUserNum();
            }
        });
        logger.info("service返回结果为 {}", JSON.toJSONString(activeUserList));
        return activeUserList;
    }

    public List<String> getActiveUserDetail(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        List<String> oldUserIds = activeUserDao.getActiveUserDetail(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(oldUserIds));
        return oldUserIds;
    }

    public int updateActive(List<ActiveUser> activeUserList) {
        return activeUserSQLDao.updateActive(activeUserList);
    }

    public List<ActiveUser> getActiveUserNumOfAllSourcesFromStatResult(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        List<ActiveUser> activeUserList;
        if (!CommonConstant.statTimeSpanOfMonth.equals(queryPara.getTimeSpan()) ) {
            activeUserList = activeUserSQLDao.getDateActiveUserNumOfAllSourcesFromStatResult(queryPara);
        }else{
            activeUserList = activeUserSQLDao.getMonthActiveUserNumOfAllSourcesFromStatResult(queryPara);
        }
        logger.info("service返回结果为 {}", JSON.toJSONString(activeUserList));
        return activeUserList;
    }
}
