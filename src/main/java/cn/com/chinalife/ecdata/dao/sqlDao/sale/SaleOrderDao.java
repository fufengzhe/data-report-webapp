package cn.com.chinalife.ecdata.dao.sqlDao.sale;

import cn.com.chinalife.ecdata.entity.sale.SaleOrder;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/2.
 */
@Repository
public interface SaleOrderDao {

    List<SaleOrder> getSaleOrderListOfProduct(QueryPara queryPara);

    List<SaleOrder> getSaleOrderListForTrendOfDate(QueryPara queryPara);

    List<SaleOrder> getSaleOrderListOfSource(QueryPara queryPara);

    List<SaleOrder> getSaleOrderDetailList(QueryPara queryPara);

    List<SaleOrder> getAllProductList();

    List<SaleOrder> getAllSourceList();

    List<SaleOrder> getSaleOrderListOfSex(QueryPara queryPara);

    List<SaleOrder> getSaleOrderListOfAge(QueryPara queryPara);

    List<SaleOrder> getSaleOrderListOfRelation(QueryPara queryPara);
}
