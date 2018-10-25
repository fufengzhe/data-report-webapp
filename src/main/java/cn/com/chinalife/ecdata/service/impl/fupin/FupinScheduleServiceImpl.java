package cn.com.chinalife.ecdata.service.impl.fupin;

import cn.com.chinalife.ecdata.entity.fupin.PageClick;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.service.InitService;
import cn.com.chinalife.ecdata.service.fupin.FupinScheduleService;
import cn.com.chinalife.ecdata.service.fupin.OrderStatService;
import cn.com.chinalife.ecdata.service.fupin.PageClickService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/6.
 */
@Service
public class FupinScheduleServiceImpl implements FupinScheduleService {
    private final Logger logger = LoggerFactory.getLogger(FupinScheduleServiceImpl.class);

    @Autowired
    PageClickService pageClickService;

    @Autowired
    OrderStatService orderStatService;
    @Autowired
    InitService initService;

    @Scheduled(cron = "0 0 1 * * ?")
    private void scheduledEntryForPageClickUpdate() throws InterruptedException, ParseException {
        // 默认更新昨天所有渠道的数据
        QueryPara queryPara = new QueryPara();
        queryPara.setStartDate(DateUtils.getYesterday());
        queryPara.setEndDate(DateUtils.getYesterday());
        int effectedRowNum;
        logger.info("开始更新扶贫相关页面的点击数据");
        effectedRowNum = this.updatePageClick(queryPara);
        logger.info("结束更新扶贫相关页面的点击数据，影响的条数为 {}", effectedRowNum);
    }

    @Scheduled(cron = "0 30 1 * * ?")
    private void scheduledEntryForOrderStatUpdate() throws InterruptedException, ParseException {
        // 默认更新昨天所有渠道的数据
        QueryPara queryPara = new QueryPara();
        queryPara.setStartDate(DateUtils.getYesterday());
        queryPara.setEndDate(DateUtils.getYesterday());
        int effectedRowNum;
        logger.info("开始更新扶贫订单相关数据");
        effectedRowNum = this.updateOrderStat(queryPara);
        logger.info("结束更新扶贫订单相关数据，影响的条数为 {}", effectedRowNum);
    }

    // 根据前端传入的日期及渠道更新相应的数据，如无渠道相关信息则更新改日期区间内所有渠道的数据
    public int updatePageClick(QueryPara queryPara) {
        List<PageClick> pageClickList = pageClickService.getPageClickListForTimeSpan(queryPara);
        int effectedRowNum = pageClickService.updatePageClick(pageClickList);
        initService.updateDataStatus(queryPara.getStartDate(), CommonConstant.statTimeSpanOfDate, CommonConstant.statIndexNameOfFupinPageClick, "扶贫相关页面点击数据", effectedRowNum);
        return effectedRowNum;
    }

    public int updateOrderStat(QueryPara queryPara) {
        //因订单有隔天变化的特点，且目前存量和增量订单较少，采取实时查询的方式进行，待订单量增大之后改为T+1更新的方式，但为了前端展示方便依然更新DATA_STATUS表
//        List<OrderStat> orderStatList = orderStatService.getOrderStatListForTimeSpan(queryPara);
//        int effectedRowNum = orderStatService.updateOrderStat(orderStatList);
        int effectedRowNum = 1;
        initService.updateDataStatus(queryPara.getStartDate(), CommonConstant.statTimeSpanOfDate, CommonConstant.statIndexNameOfFupinOrderStat, "扶贫商品订单数据", effectedRowNum);
        return effectedRowNum;
    }
}
