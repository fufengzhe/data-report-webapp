package cn.com.chinalife.ecdata.service.fupin;

import cn.com.chinalife.ecdata.entity.fupin.OrderStat;
import cn.com.chinalife.ecdata.entity.query.QueryPara;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/2.
 */
public interface OrderStatService {

    List<OrderStat> getOrderStatListForTimeSpan(QueryPara queryPara);

    int updateOrderStat(List<OrderStat> orderStatList);

    List<List<OrderStat>> getOrderStatList(QueryPara queryPara);

    List<OrderStat> getPageClickIPInfoList(QueryPara queryPara);

    int updateOrderIPInfo(List<OrderStat> orderIPInfoList);

    List<List<OrderStat>> getOrderStatIPDistributeList(QueryPara queryPara);

    List<OrderStat> getOrderFromToAreaInfoList(QueryPara queryPara);

    int updateOrderFromToAreaInfo(List<OrderStat> orderFromToAreaInfoList);

    List<List<OrderStat>> getOrderFromToAreaFlowInfo(QueryPara queryPara);

    List<OrderStat> getFromList(QueryPara queryPara);

    List<OrderStat> getToList(QueryPara queryPara);

    List<OrderStat> getOrderExpressInfo(QueryPara queryPara);

    List<List<OrderStat>> getOrderEvaluateInfo(QueryPara queryPara);

    List<String> getEvaluateValueList();
}
