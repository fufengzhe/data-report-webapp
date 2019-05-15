package cn.com.chinalife.ecdata.service.impl.sale;

import cn.com.chinalife.ecdata.dao.sqlDao.sale.SaleOrderDao;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.sale.SaleOrder;
import cn.com.chinalife.ecdata.service.sale.SaleOrderService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.DataSourceContextHolder;
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
public class SaleOrderServiceImpl implements SaleOrderService {
    private final Logger logger = LoggerFactory.getLogger(SaleOrderServiceImpl.class);
    @Autowired
    SaleOrderDao saleOrderDao;

    public List<List<SaleOrder>> getSaleOrderList(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        List<List<SaleOrder>> lists = new ArrayList<List<SaleOrder>>();
        DataSourceContextHolder.setDbType(CommonConstant.saleOrderDataSource);
        List<SaleOrder> saleOrderListOfProduct = saleOrderDao.getSaleOrderListOfProduct(queryPara);
        lists.add(saleOrderListOfProduct);
        List<SaleOrder> saleOrderListForTrendOfDate = saleOrderDao.getSaleOrderListForTrendOfDate(queryPara);
        lists.add(saleOrderListForTrendOfDate);
        logger.info("service返回结果为 {}", JSON.toJSONString(lists));
        return lists;
    }

    public List<SaleOrder> getSaleOrderListOfProduct(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.saleOrderDataSource);
        List<SaleOrder> saleOrderListOfProduct = saleOrderDao.getSaleOrderListOfProduct(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(saleOrderListOfProduct));
        return saleOrderListOfProduct;
    }

}
