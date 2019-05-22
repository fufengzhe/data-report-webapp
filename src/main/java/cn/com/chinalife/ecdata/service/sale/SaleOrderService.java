package cn.com.chinalife.ecdata.service.sale;

import cn.com.chinalife.ecdata.entity.sale.SaleOrder;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/2.
 */
public interface SaleOrderService {

    List<List<SaleOrder>> getSaleOrderList(QueryPara queryPara);

    List<List<SaleOrder>> getSaleOrderListOfProductAndSource(QueryPara queryPara);

    List<SaleOrder> getSaleOrderDetailList(QueryPara queryPara);

    Workbook getOrderDetailWorkBook(List<SaleOrder> saleOrderDetailsList);
}
