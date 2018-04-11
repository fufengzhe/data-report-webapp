package cn.com.chinalife.ecdata.service.impl.trade;

import cn.com.chinalife.ecdata.dao.sqlDao.trade.PlatformTradeAmountDao;
import cn.com.chinalife.ecdata.entity.trade.Premium;
import cn.com.chinalife.ecdata.service.trade.PlatformTradeAmountService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.CommonUtils;
import cn.com.chinalife.ecdata.utils.DataSourceContextHolder;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
@Service
public class PlatformTradeAmountServiceImpl implements PlatformTradeAmountService {
    private final Logger logger = LoggerFactory.getLogger(PlatformTradeAmountServiceImpl.class);
    @Autowired
    PlatformTradeAmountDao platformTradeAmountDao;

    public List<Premium> getPlatformTradeAmount() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<Premium> premiumList = platformTradeAmountDao.getPlatformTradeAmount();
        this.calRatio(premiumList);
        logger.info("service返回结果为 {}", JSON.toJSONString(premiumList));
        return premiumList;
    }

    public List<Premium> getPlatformTradeNum() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<Premium> premiumList = platformTradeAmountDao.getPlatformTradeNum();
        this.calRatio(premiumList);
        logger.info("service返回结果为 {}", JSON.toJSONString(premiumList));
        return premiumList;
    }

    public Premium getPlatformSignRatio() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        Premium premium = platformTradeAmountDao.getPlatformSignRatio();
        premium.setYearAmountStr(premium.getYearAmount() + "%");
        premium.setCompleteRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(premium.getYearAmount(), premium.getYearGoal(), 4)));
        logger.info("service返回结果为 {}", JSON.toJSONString(premium));
        return premium;
    }

    private void calRatio(List<Premium> premiumList) {
        for (Premium premium : premiumList) {
            premium.setDayRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(premium.getDayAmount().subtract(premium.getLastDayAmount()), premium.getLastDayAmount(), 4)));
            premium.setMonthRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(premium.getMonthAmount().subtract(premium.getLastMonthAmount()), premium.getLastMonthAmount(), 4)));
            if (!"总计".equals(premium.getSecondName())) {
                premium.setCompleteRatio(CommonUtils.getPercentageStr(CommonUtils.divideWithXPrecision(premium.getYearAmount(), premium.getYearGoal(), 4)));
            }
        }
    }
}
