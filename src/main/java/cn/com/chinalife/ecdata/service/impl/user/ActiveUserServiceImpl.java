package cn.com.chinalife.ecdata.service.impl.user;

import cn.com.chinalife.ecdata.dao.noSqlDao.user.ActiveUserDao;
import cn.com.chinalife.ecdata.dao.sqlDao.InitDao;
import cn.com.chinalife.ecdata.dao.sqlDao.user.ActiveUserSQLDao;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.ActiveUser;
import cn.com.chinalife.ecdata.entity.user.UserSource;
import cn.com.chinalife.ecdata.service.user.ActiveUserService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.DateUtils;
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
            activeUser.setUserSource(userSourceCodeAndNameMap.get(activeUser.getUserSource()) == null ? activeUser.getUserSource(): userSourceCodeAndNameMap.get(activeUser.getUserSource()));
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
        if (activeUserList != null && activeUserList.size() > 0) {
            return activeUserSQLDao.updateActive(activeUserList);
        } else {
            return 0;
        }
    }

    public List<ActiveUser> getActiveUserNumOfAllSourcesFromStatResult(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        List<ActiveUser> activeUserList;
        if (!CommonConstant.statTimeSpanOfMonth.equals(queryPara.getTimeSpan())) {
            activeUserList = activeUserSQLDao.getDateActiveUserNumOfAllSourcesFromStatResult(queryPara);
        } else {
            activeUserList = activeUserSQLDao.getMonthActiveUserNumOfAllSourcesFromStatResultWithoutEBaoZhang(queryPara);
            List<ActiveUser> eBaoZhangList = activeUserSQLDao.getMonthActiveUserNumOfAllSourcesFromStatResultForEBaoZhang(queryPara);
            activeUserList.addAll(eBaoZhangList);
            Collections.sort(activeUserList, new Comparator<ActiveUser>() {
                public int compare(ActiveUser o1, ActiveUser o2) {
                    return o2.getActiveUserNum() - o1.getActiveUserNum();
                }
            });
        }
        logger.info("service返回结果为 {}", JSON.toJSONString(activeUserList));
        return activeUserList;
    }

    public List<List<ActiveUser>> queryOfficialSiteActiveNum(QueryPara queryPara) {
        return activeUserDao.queryOfficialSiteActiveNum(queryPara);
    }

    public String[][] getTableContent(List<ActiveUser> activeUserList, String[] title) {
        String[][] tableContent = new String[activeUserList.size() + 1][title.length];
        for (int i = 0; i < title.length; i++) {
            tableContent[0][i] = title[i];
        }
        for (int i = 0; i < activeUserList.size(); i++) {
            tableContent[i + 1][0] = activeUserList.get(i).getStartDate();
            tableContent[i + 1][1] = activeUserList.get(i).getActiveUserNum() + "";
        }
        return tableContent;
    }

    public List<ActiveUser> getActiveUserNumOfEBaoZhang(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        List<ActiveUser> activeUserList = activeUserSQLDao.getActiveUserNumOfEBaoZhang(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(activeUserList));
        return activeUserList;
    }

    public List<String> getLatestDateOfEBaoZhang() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        List<String> latestDateList = activeUserSQLDao.getLatestDateOfEBaoZhang();
        logger.info("service返回结果为 {}", JSON.toJSONString(latestDateList));
        return latestDateList;
    }

    public List<List<ActiveUser>> getActiveUserSummaryList() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        List<List<ActiveUser>> lists = new ArrayList<List<ActiveUser>>();

        QueryPara queryPara = new QueryPara();
        queryPara.setStartDate(DateUtils.getYesterday());
        queryPara.setEndDate(DateUtils.getYesterday());
        queryPara.setTimeSpan(CommonConstant.statTimeSpanOfDate);
        List<ActiveUser> activeUserListOfDate = this.getActiveUserNumOfAllSourcesFromStatResult(queryPara);
        lists.add(activeUserListOfDate);

        queryPara.setStartDate(DateUtils.getMonthUsingYesterday(DateUtils.getYesterday()));
        queryPara.setTimeSpan(CommonConstant.statTimeSpanOfMonth);
        List<ActiveUser> activeUserListOfMonth = this.getActiveUserNumOfAllSourcesFromStatResult(queryPara);
        lists.add(activeUserListOfMonth);

        queryPara.setStartDate(DateUtils.getBeforeXDay(7));
        queryPara.setEndDate(DateUtils.getBeforeXDay(1));
        queryPara.setTimeSpan(CommonConstant.statTimeSpanOfDate);
        List<ActiveUser> activeUserListForTrendOfDate = activeUserSQLDao.getActiveUserNumOfAllSourcesFromStatResultForTrendOfDate(queryPara);
        lists.add(activeUserListForTrendOfDate);
        logger.info("service返回结果为 {}", JSON.toJSONString(lists));
        return lists;
    }
}
