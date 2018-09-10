package cn.com.chinalife.ecdata.service.impl;

import cn.com.chinalife.ecdata.dao.sqlDao.InitDao;
import cn.com.chinalife.ecdata.entity.UpdateResult;
import cn.com.chinalife.ecdata.entity.user.UserSource;
import cn.com.chinalife.ecdata.service.InitService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.DataSourceContextHolder;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<UserSource> getALLNewUserSource() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<UserSource> userSourceList = initDao.getNewUserSourceOfAll();
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

    public Map<String, String> getAllNewUserSourceCodeAndName() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<UserSource> userSourceList = initDao.getNewUserSourceOfAll();
        logger.info("service返回结果为 {}", JSON.toJSONString(userSourceList));
        return this.getMapUsingList(userSourceList);
    }

    public Map<String, String> getAllOldUserSourceCodeAndName() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<UserSource> userSourceList = initDao.getOldUserSourceOfAll();
        logger.info("service返回结果为 {}", JSON.toJSONString(userSourceList));
        return this.getMapUsingList(userSourceList);
    }

    public Map<String, String> getNewUserSourceCodeAndName() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<UserSource> userSourceList = initDao.getNewUserSource();
        logger.info("service返回结果为 {}", JSON.toJSONString(userSourceList));
        return this.getMapUsingList(userSourceList);
    }


    Map<String, String> getMapUsingList(List<UserSource> userSourceList) {
        Map<String, String> map = new HashMap<String, String>();
        for (UserSource userSource : userSourceList) {
            map.put(userSource.getUserSource(), userSource.getUserSourceName());
        }
        return map;
    }

    public int updateDataStatus(List<UpdateResult> updateResultList) {
        if (updateResultList != null && updateResultList.size() > 0) {
            return initDao.updateDataStatus(updateResultList);
        } else {
            return 0;
        }
    }

    public int updateDataStatus(UpdateResult updateResult) {
        List<UpdateResult> list = new ArrayList<UpdateResult>();
        list.add(updateResult);
        logger.info("更新数据准备状态，数据状态标识为 {}", JSON.toJSONString(updateResult));
        return this.updateDataStatus(list);
    }

    public int updateDataStatus(String statTime, String statTimeSpan, String indexName, String indexDesc, Integer effectedRowNum) {
        UpdateResult updateResult = new UpdateResult();
        updateResult.setStatTime(statTime);
        updateResult.setStatTimeSpan(statTimeSpan);
        updateResult.setIndexName(indexName);
        updateResult.setIndexDesc(indexDesc);
        updateResult.setEffectedRowNum(effectedRowNum);
        return this.updateDataStatus(updateResult);
    }

    public List<UpdateResult> getUpdateResult() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<UpdateResult> updateResultList = initDao.getUpdateResult();
        logger.info("service返回结果为 {}", JSON.toJSONString(updateResultList));
        return updateResultList;
    }
}

