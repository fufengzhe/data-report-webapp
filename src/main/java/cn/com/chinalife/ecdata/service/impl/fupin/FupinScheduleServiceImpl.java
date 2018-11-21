package cn.com.chinalife.ecdata.service.impl.fupin;

import cn.com.chinalife.ecdata.entity.fupin.OrderStat;
import cn.com.chinalife.ecdata.entity.fupin.PageClick;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.service.InitService;
import cn.com.chinalife.ecdata.service.fupin.FupinScheduleService;
import cn.com.chinalife.ecdata.service.fupin.OrderStatService;
import cn.com.chinalife.ecdata.service.fupin.PageClickService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.DataSourceContextHolder;
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

    @Scheduled(cron = "0 0 8 * * ?")
    private void scheduledEntryForPageClickIPInfoUpdate() throws InterruptedException, ParseException {
        // 默认更新昨天所有渠道的数据
        QueryPara queryPara = new QueryPara();
        queryPara.setStartDate(DateUtils.getYesterday());
        queryPara.setEndDate(DateUtils.getYesterday());
        int effectedRowNum;
        logger.info("开始更新扶贫相关页面的点击IP分布数据");
        effectedRowNum = this.updatePageClickIPInfo(queryPara);
        logger.info("结束更新扶贫相关页面的点击IP分布数据，影响的条数为 {}", effectedRowNum);
    }

    @Scheduled(cron = "0 30 8 * * ?")
    private void scheduledEntryForOrderIPInfoUpdate() throws InterruptedException, ParseException {
        // 默认更新昨天所有渠道的数据
        QueryPara queryPara = new QueryPara();
        queryPara.setStartDate(DateUtils.getYesterday());
        queryPara.setEndDate(DateUtils.getYesterday());
        int effectedRowNum;
        logger.info("开始更新扶贫相关订单的下单IP分布数据");
        effectedRowNum = this.updateOrderIPInfo(queryPara);
        logger.info("结束更新扶贫相关订单的下单IP分布数据，影响的条数为 {}", effectedRowNum);
    }

    @Scheduled(cron = "0 40 8 * * ?")
    private void scheduledEntryForOrderFromToAreaInfoUpdate() throws InterruptedException, ParseException {
        // 默认更新昨天所有渠道的数据
        QueryPara queryPara = new QueryPara();
        queryPara.setStartDate(DateUtils.getYesterday());
        queryPara.setEndDate(DateUtils.getYesterday());
        int effectedRowNum;
        logger.info("开始更新扶贫相关订单的下单及收货地区分布数据");
        effectedRowNum = this.updateOrderFromToAreaInfo(queryPara);
        logger.info("结束更新扶贫相关订单的下单及收货地区分布数据，影响的条数为 {}", effectedRowNum);
    }

    // 根据前端传入的日期及渠道更新相应的数据，如无渠道相关信息则更新改日期区间内所有渠道的数据
    public int updatePageClick(QueryPara queryPara) {
        List<PageClick> pageClickList = pageClickService.getPageClickListForTimeSpan(queryPara);
        int effectedRowNum = pageClickService.updatePageClick(pageClickList);
        initService.updateDataStatus(queryPara.getStartDate(), CommonConstant.statTimeSpanOfDate, CommonConstant.statIndexNameOfFupinPageClick, "扶贫相关页面点击数据", effectedRowNum);
        return effectedRowNum;
    }

    public int updatePageClickIPInfo(QueryPara queryPara) {
        List<PageClick> pageClickIPInfoList = pageClickService.getPageClickIPInfoList(queryPara);
        int effectedRowNum = pageClickService.updatePageClickIPInfo(pageClickIPInfoList);
        initService.updateDataStatus(queryPara.getStartDate(), CommonConstant.statTimeSpanOfDate, CommonConstant.statIndexNameOfFupinPageClickIPInfo, "扶贫相关页面点击IP分布数据", effectedRowNum);
        return effectedRowNum;
    }

    public int updateOrderIPInfo(QueryPara queryPara) {
        List<OrderStat> orderIPInfoList = orderStatService.getPageClickIPInfoList(queryPara);
        int effectedRowNum = orderStatService.updateOrderIPInfo(orderIPInfoList);
        initService.updateDataStatus(queryPara.getStartDate(), CommonConstant.statTimeSpanOfDate, CommonConstant.statIndexNameOfFupinOrderIPInfo, "扶贫相关订单下单IP分布数据", effectedRowNum);
        return effectedRowNum;
    }

    public int updateOrderFromToAreaInfo(QueryPara queryPara) {
        List<OrderStat> orderFromToAreaInfoList = orderStatService.getOrderFromToAreaInfoList(queryPara);
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        int effectedRowNum = orderStatService.updateOrderFromToAreaInfo(orderFromToAreaInfoList);
        initService.updateDataStatus(queryPara.getStartDate(), CommonConstant.statTimeSpanOfDate, CommonConstant.statIndexNameOfFupinOrderFromToAreaInfo, "扶贫相关订单下单及收货地区分布数据", effectedRowNum);
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
