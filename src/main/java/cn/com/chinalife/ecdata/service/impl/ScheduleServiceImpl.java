package cn.com.chinalife.ecdata.service.impl;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.trade.Premium;
import cn.com.chinalife.ecdata.entity.user.ActiveUser;
import cn.com.chinalife.ecdata.entity.user.RegisterUser;
import cn.com.chinalife.ecdata.service.ScheduleService;
import cn.com.chinalife.ecdata.service.trade.LifePremiumService;
import cn.com.chinalife.ecdata.service.user.ActiveUserService;
import cn.com.chinalife.ecdata.service.user.RegisterUserService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/6.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    @Autowired
    RegisterUserService registerUserService;

    @Autowired
    ActiveUserService activeUserService;

    @Autowired
    LifePremiumService lifePremiumService;

    @Scheduled(cron = "0 0 1 * * ?")
    private void scheduledEntry() throws InterruptedException {
        // 默认更新昨天所有渠道的数据
        QueryPara queryPara = prepareQueryPara();
        int effectedRowNum;

        logger.info("开始更新注册相关的数据");
        effectedRowNum = updateRegister(queryPara);
        logger.info("结束更新注册相关的数据，受影响的条数为 {}", effectedRowNum);

        logger.info("开始更新活跃相关的天维度数据");
        queryPara.setTimeSpan(CommonConstant.statTimeSpanOfDate);
        effectedRowNum = updateActive(queryPara);
        logger.info("结束更新活跃相关的天维度数据，受影响的条数为 {}", effectedRowNum);

        logger.info("开始更新活跃相关的月维度数据");
        queryPara.setStartDate(DateUtils.getMonthBeginDateUsingYesterday(DateUtils.getYesterday()));
        queryPara.setTimeSpan(CommonConstant.statTimeSpanOfMonth);
        effectedRowNum = updateActive(queryPara);
        logger.info("结束更新活跃相关的月维度数据，受影响的条数为 {}", effectedRowNum);

        logger.info("开始更新寿险保费相关数据");
        effectedRowNum = updateLifePremium(queryPara);
        logger.info("结束更新寿险保费相关数据，受影响的条数为 {}", effectedRowNum);
    }

    private QueryPara prepareQueryPara() {
        QueryPara queryPara = new QueryPara();
        String yesterday = DateUtils.getYesterday();
        queryPara.setStartDate(yesterday);
        queryPara.setEndDate(yesterday);
        return queryPara;
    }

    // 根据前端传入的日期及渠道更新相应的数据，如无渠道相关信息则更新改日期区间内所有渠道的数据
    public int updateRegister(QueryPara queryPara) {
        List<RegisterUser> registerUserList = registerUserService.getRegisterUserNumOfAllSources(queryPara);
        return registerUserService.updateRegister(registerUserList);
    }

    public int updateActive(QueryPara queryPara) {
        List<ActiveUser> activeUserList = activeUserService.getActiveUserNumOfAllSources(queryPara);
        return activeUserService.updateActive(activeUserList);
    }

    public int updateLifePremium(QueryPara queryPara) {
        int effectedRowNum = 0;

        logger.info("开始删除历史所有寿险保费相关数据");
        int temp = lifePremiumService.deleteAllExistedRecord(CommonConstant.statIndexNameOfLifePremium);
        logger.info("结束删除历史所有寿险保费相关数据");
        effectedRowNum += temp;

        logger.info("开始插入天维度寿险保费相关数据");
        queryPara.setTimeSpan(CommonConstant.statTimeSpanOfDate);
        List<Premium> premiumList = lifePremiumService.getLifePremiumDetail(queryPara);
        temp = lifePremiumService.updateLifePremium(premiumList);
        effectedRowNum += temp;
        logger.info("结束插入天维度寿险保费相关数据");

        logger.info("开始插入月维度寿险保费相关数据");
        queryPara.setTimeSpan(CommonConstant.statTimeSpanOfMonth);
        premiumList = lifePremiumService.getLifePremiumDetail(queryPara);
        temp = lifePremiumService.updateLifePremium(premiumList);
        effectedRowNum += temp;
        logger.info("结束插入月维度寿险保费相关数据");

        logger.info("开始插入年维度寿险保费相关数据");
        queryPara.setTimeSpan(CommonConstant.statTimeSpanOfYear);
        premiumList = lifePremiumService.getLifePremiumDetail(queryPara);
        temp = lifePremiumService.updateLifePremium(premiumList);
        effectedRowNum += temp;
        logger.info("结束插入年维度寿险保费相关数据");
        return effectedRowNum;
    }


}
