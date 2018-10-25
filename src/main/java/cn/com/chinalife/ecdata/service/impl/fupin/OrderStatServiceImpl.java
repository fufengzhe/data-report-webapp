package cn.com.chinalife.ecdata.service.impl.fupin;

import cn.com.chinalife.ecdata.dao.sqlDao.fupin.OrderStatDao;
import cn.com.chinalife.ecdata.entity.fupin.OrderStat;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.service.fupin.OrderStatService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.CommonUtils;
import cn.com.chinalife.ecdata.utils.DataSourceContextHolder;
import cn.com.chinalife.ecdata.utils.DateUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
@Service
public class OrderStatServiceImpl implements OrderStatService {
    private final Logger logger = LoggerFactory.getLogger(OrderStatServiceImpl.class);
    @Autowired
    OrderStatDao orderStatDao;

    public List<OrderStat> getOrderStatListForTimeSpan(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.fupinDataSource);
        List<OrderStat> orderStatList = orderStatDao.getOrderStatListForTimeSpan(queryPara);
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        logger.info("service返回结果为 {}", JSON.toJSONString(orderStatList));
        return orderStatList;
    }

    public int updateOrderStat(List<OrderStat> orderStatList) {
        if (orderStatList != null && orderStatList.size() > 0) {
            return orderStatDao.updateOrderStat(orderStatList);
        } else {
            return 0;
        }
    }

    public List<List<OrderStat>> getOrderStatList() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        List<List<OrderStat>> lists = new ArrayList<List<OrderStat>>();
        QueryPara queryPara = new QueryPara();
        queryPara.setStartDate(DateUtils.getYesterday());
        queryPara.setEndDate(DateUtils.getYesterday());
        List<OrderStat> orderStatListOfDate = this.getOrderStatListForTimeSpanFromStatTable(queryPara);
        lists.add(orderStatListOfDate);

        queryPara.setStartDate(DateUtils.getBeforeXDay(7));
        queryPara.setEndDate(DateUtils.getBeforeXDay(1));
        List<OrderStat> orderStatListForTrendOfDate = this.getOrderStatListForTimeSpanTrendFromStatTable(queryPara);
        lists.add(orderStatListForTrendOfDate);
        logger.info("service返回结果为 {}", JSON.toJSONString(lists));
        return lists;
    }

    public List<OrderStat> getOrderStatListForTimeSpanFromStatTable(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.fupinDataSource);
        queryPara.setWhereCondition(CommonUtils.getWhereConditionUsingPara(queryPara.getWhereCondition()));
        List<OrderStat> orderStatList = orderStatDao.getOrderStatListForTimeSpanFromStatTable(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(orderStatList));
        return orderStatList;
    }

    public List<OrderStat> getOrderProductList() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.fupinDataSource);
        List<OrderStat> orderProductList = orderStatDao.getOrderProductList();
        logger.info("service返回结果为 {}", JSON.toJSONString(orderProductList));
        return orderProductList;
    }

    private List<OrderStat> getOrderStatListForTimeSpanTrendFromStatTable(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.fupinDataSource);
        List<OrderStat> orderStatList = orderStatDao.getOrderStatListForTimeSpanTrendFromStatTable(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(orderStatList));
        return orderStatList;
    }

}
