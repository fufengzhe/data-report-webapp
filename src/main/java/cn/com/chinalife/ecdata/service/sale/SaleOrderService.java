package cn.com.chinalife.ecdata.service.sale;

import cn.com.chinalife.ecdata.entity.sale.SaleOrder;
import cn.com.chinalife.ecdata.entity.query.QueryPara;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/2.
 */
public interface SaleOrderService {

    List<List<SaleOrder>> getSaleOrderList(QueryPara queryPara);
    List<SaleOrder> getSaleOrderListOfProduct(QueryPara queryPara);
}
