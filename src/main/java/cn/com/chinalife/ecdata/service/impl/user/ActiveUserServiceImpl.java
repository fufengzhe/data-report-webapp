package cn.com.chinalife.ecdata.service.impl.user;

import cn.com.chinalife.ecdata.dao.noSqlDao.user.ActiveUserDao;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.ActiveUser;
import cn.com.chinalife.ecdata.service.user.ActiveUserService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
@Service
public class ActiveUserServiceImpl implements ActiveUserService {
    private final Logger logger = LoggerFactory.getLogger(ActiveUserServiceImpl.class);
    @Autowired
    ActiveUserDao activeUserDao;

    public ActiveUser getActiveUserNum(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        ActiveUser activeUser = activeUserDao.getActiveUserNum(queryPara);
        activeUser.setUserSource(queryPara.getUserSource());
        activeUser.setStartDate(queryPara.getStartDate());
        activeUser.setEndDate(queryPara.getEndDate());
        logger.info("service返回结果为 {}", JSON.toJSONString(activeUser));
        return activeUser;
    }
}
