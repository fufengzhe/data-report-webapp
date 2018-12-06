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

    List<String> getFuPinSellerIDList();

    List<String> getFuPinSellerIDListForSpecifiedArea(String specifiedArea);

    List<OrderStat> getOnlineRetailOrderIPList(QueryPara queryPara);

    List<OrderStat> getOnlineGroupBuyOrderIPList(QueryPara queryPara);

    int updateOrderIPInfo(List<OrderStat> orderIPInfoList);

    List<OrderStat> getOrderStatCompanyDistributeList(QueryPara queryPara);

    List<OrderStat> getOrderStatLocationDistributeList(QueryPara queryPara);

    List<OrderStat> getOrderAmountListForSellerDimension(QueryPara queryPara);

    List<String> getFuPinSellerNameList();

    List<OrderStat> getFuPinSellerAreaList();

    List<OrderStat> getOnlineOrderFromToAreaList(QueryPara queryPara);

    int updateOrderFromToAreaInfo(List<OrderStat> orderFromToAreaInfoList);

    List<OrderStat> getOrderFromToInfoList(QueryPara queryPara);

    List<OrderStat> getOrderFromInfoList(QueryPara queryPara);

    List<OrderStat> getOrderToInfoList(QueryPara queryPara);

    List<OrderStat> getFromList(QueryPara queryPara);

    List<OrderStat> getToList(QueryPara queryPara);

    List<OrderStat> getOrderAmountListForAreaOfOfflineMail(QueryPara queryPara);

    List<OrderStat> getDanQuanWaterOrderAmount(QueryPara queryPara);

    List<OrderStat> getOnlineRetailAndJiCaiList(QueryPara queryPara);

    List<OrderStat> getOfflineMailList(QueryPara queryPara);

    List<OrderStat> getExpressDisList(QueryPara queryPara);

    List<OrderStat> getSupplyBillTitleListFromOracle();

    List<OrderStat> getSupplyBillTitleList(QueryPara queryPara);
}
