package cn.com.chinalife.ecdata.service.impl.user;

import cn.com.chinalife.ecdata.dao.sqlDao.user.LogUserDao;
import cn.com.chinalife.ecdata.entity.user.LogUser;
import cn.com.chinalife.ecdata.service.user.UserLogService;
import cn.com.chinalife.ecdata.utils.AuthUtils;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.CommonUtils;
import cn.com.chinalife.ecdata.utils.DataSourceContextHolder;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xiexiangyu on 2018/3/7.
 */
@Service
public class UserLogServiceImpl implements UserLogService {
    private final Logger logger = LoggerFactory.getLogger(UserLogServiceImpl.class);
    @Autowired
    LogUserDao logUserDao;

    public LogUser login(LogUser requestUser) throws Exception {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(requestUser));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        requestUser.setPassword(CommonUtils.getMD5(requestUser.getPassword()));
        LogUser logUser = logUserDao.findUser(requestUser);
//        LogUser logUser = this.findLogUserUsingAuthUtils(requestUser);
        logger.info("service返回结果为 {}", JSON.toJSONString(logUser));
        return logUser;
    }

    private LogUser findLogUserUsingAuthUtils(LogUser requestUser) throws Exception {
        LogUser logUser = null;
        Class.forName("cn.com.chinalife.ecdata.utils.AuthUtils");
        for (LogUser temp : AuthUtils.existedAuthLogUserList) {
            if (temp.getUsername().equals(requestUser.getUsername()) && temp.getPassword().equals(CommonUtils.getMD5(requestUser.getPassword()))) {
                logUser = new LogUser();
                logUser.setUsername(temp.getUsername());
//                logUser.setPassword(temp.getPassword());
                logUser.setResources(temp.getResources());
                break;
            }
        }
        return logUser;
    }
}
