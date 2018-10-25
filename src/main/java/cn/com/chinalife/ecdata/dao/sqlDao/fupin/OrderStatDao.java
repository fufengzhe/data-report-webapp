package cn.com.chinalife.ecdata.dao.sqlDao.fupin;

import cn.com.chinalife.ecdata.entity.fupin.OrderStat;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/2.
 */
@Repository
public interface OrderStatDao {

    List<OrderStat> getOrderStatListForTimeSpan(QueryPara queryPara);

    int updateOrderStat(List<OrderStat> orderStatList);

    List<OrderStat> getOrderStatListForTimeSpanFromStatTable(QueryPara queryPara);

    List<OrderStat> getOrderStatListForTimeSpanTrendFromStatTable(QueryPara queryPara);

    List<OrderStat> getOrderProductList();
}
