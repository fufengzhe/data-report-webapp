package cn.com.chinalife.ecdata.service.impl;

import cn.com.chinalife.ecdata.dao.sqlDao.InitDao;
import cn.com.chinalife.ecdata.entity.user.UserSource;
import cn.com.chinalife.ecdata.service.InitService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.DataSourceContextHolder;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/6.
 */
@Service
public class InitServiceImpl implements InitService {
    private final Logger logger = LoggerFactory.getLogger(InitServiceImpl.class);
    @Autowired
    InitDao initDao;

    public List<UserSource> getNewUserSource() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<UserSource> userSourceList = initDao.getNewUserSource();
        logger.info("service返回结果为 {}", JSON.toJSONString(userSourceList));
        return userSourceList;
    }

    public List<UserSource> getOldUserSource() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<UserSource> userSourceList = initDao.getOldUserSource();
        logger.info("service返回结果为 {}", JSON.toJSONString(userSourceList));
        return userSourceList;
    }
}
