package cn.com.chinalife.ecdata.service.sale;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.sale.SaleUser;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/2.
 */
public interface SaleUserService {

    List<List<SaleUser>> getSaleUserRegisterNumList(QueryPara queryPara);

    List<List<SaleUser>> getSaleUserRegisterNumListOfSourceAndHour(QueryPara queryPara);

    List<List<SaleUser>> getSaleUserLogNumList(QueryPara queryPara);

    List<List<SaleUser>> getSaleUserLogNumListOfSourceAndMode(QueryPara queryPara);
}
