package cn.com.chinalife.ecdata.service.impl.sale;

import cn.com.chinalife.ecdata.dao.sqlDao.sale.SaleOrderDao;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.sale.SaleOrder;
import cn.com.chinalife.ecdata.service.sale.SaleOrderService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.CommonUtils;
import cn.com.chinalife.ecdata.utils.DataSourceContextHolder;
import cn.com.chinalife.ecdata.utils.ExcelUtils;
import com.alibaba.fastjson.JSON;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        DataSourceContextHolder.setDbType(CommonConstant.saleStatisticsDataSource);
        List<SaleOrder> saleOrderListOfProduct = saleOrderDao.getSaleOrderListOfProduct(queryPara);
        lists.add(saleOrderListOfProduct);
        List<SaleOrder> saleOrderListOfSource = saleOrderDao.getSaleOrderListOfSource(queryPara);
        lists.add(saleOrderListOfSource);
        List<SaleOrder> saleOrderListForTrendOfDate = saleOrderDao.getSaleOrderListForTrendOfDate(queryPara);
        lists.add(saleOrderListForTrendOfDate);
        logger.info("service返回结果为 {}", JSON.toJSONString(lists));
        return lists;
    }

    public List<List<SaleOrder>> getSaleOrderListOfProductAndSource(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        List<List<SaleOrder>> lists = new ArrayList<List<SaleOrder>>();
        DataSourceContextHolder.setDbType(CommonConstant.saleStatisticsDataSource);
        queryPara.setWhereCondition(CommonUtils.getWhereConditionUsingPara(queryPara.getWhereCondition()));
        List<SaleOrder> saleOrderListOfProduct = saleOrderDao.getSaleOrderListOfProduct(queryPara);
        lists.add(saleOrderListOfProduct);
        List<SaleOrder> saleOrderListOfSource = saleOrderDao.getSaleOrderListOfSource(queryPara);
        lists.add(saleOrderListOfSource);
        List<SaleOrder> saleOrderListForTrendOfDate = saleOrderDao.getSaleOrderListForTrendOfDate(queryPara);
        lists.add(saleOrderListForTrendOfDate);
        logger.info("service返回结果为 {}", JSON.toJSONString(lists));
        return lists;
    }

    public List<SaleOrder> getSaleOrderDetailList(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        DataSourceContextHolder.setDbType(CommonConstant.saleStatisticsDataSource);
        List<SaleOrder> saleOrderDetailList = saleOrderDao.getSaleOrderDetailList(queryPara);
        logger.info("service返回结果为 {}", JSON.toJSONString(saleOrderDetailList));
        return saleOrderDetailList;
    }

    public Workbook getOrderDetailWorkBook(List<SaleOrder> dataBeanList) {
        Map<String, String> columnFieldMap = new LinkedHashMap<String, String>();
        Map<String, Integer> fieldTypeMap = new HashMap<String, Integer>(); // 字段和该字段
        columnFieldMap.put("渠道", "source");
        columnFieldMap.put("订单号", "orderId");
        columnFieldMap.put("被保险人姓名", "personName");
        columnFieldMap.put("证件类型", "certificateType");
        columnFieldMap.put("证件号", "certificateNo");
        columnFieldMap.put("性别", "sex");
        columnFieldMap.put("年龄", "age");
        fieldTypeMap.put("age", ExcelUtils.INTEGER_FORMAT);
        columnFieldMap.put("投保日期", "orderDate");
        columnFieldMap.put("生效日期", "startDate");
        columnFieldMap.put("手机号", "mobilePhone");
        columnFieldMap.put("邮箱", "email");
        columnFieldMap.put("保费金额", "totalPremium");
        fieldTypeMap.put("totalPremium", ExcelUtils.DOUBLE_FORMAT);
        columnFieldMap.put("支付状态", "payStatus");
        columnFieldMap.put("订单状态", "orderStatus");
        columnFieldMap.put("数据提取日期", "extractionDate");
        return ExcelUtils.getWorkBook(columnFieldMap, fieldTypeMap, dataBeanList, null);
    }

    public List<List<SaleOrder>> getApplicantAttributeList(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        List<List<SaleOrder>> lists = new ArrayList<List<SaleOrder>>();
        DataSourceContextHolder.setDbType(CommonConstant.saleStatisticsDataSource);
        queryPara.setWhereCondition(CommonUtils.getWhereConditionUsingPara(queryPara.getWhereCondition()));
        queryPara.setWhereCondition1(CommonUtils.getWhereConditionUsingPara(queryPara.getWhereCondition1()));
        queryPara.setWhereCondition2(CommonUtils.getWhereConditionUsingPara(queryPara.getWhereCondition2()));
        List<SaleOrder> saleOrderListOfSex = saleOrderDao.getSaleOrderListOfSex(queryPara);
        lists.add(saleOrderListOfSex);
        List<SaleOrder> saleOrderListOfAge = saleOrderDao.getSaleOrderListOfAge(queryPara);
        lists.add(saleOrderListOfAge);
        List<SaleOrder> saleOrderListOfRelation = saleOrderDao.getSaleOrderListOfRelation(queryPara);
        lists.add(saleOrderListOfRelation);
        logger.info("service返回结果为 {}", JSON.toJSONString(lists));
        return lists;
    }

    public List<SaleOrder> getAllProductList() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.saleStatisticsDataSource);
        List<SaleOrder> saleOrderList = saleOrderDao.getAllProductList();
        logger.info("service返回结果为 {}", JSON.toJSONString(saleOrderList));
        return saleOrderList;
    }

    public List<SaleOrder> getAllSourceList() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.saleStatisticsDataSource);
        List<SaleOrder> saleOrderList = saleOrderDao.getAllSourceList();
        logger.info("service返回结果为 {}", JSON.toJSONString(saleOrderList));
        return saleOrderList;
    }

    public List<SaleOrder> getAllStatusList() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.saleStatisticsDataSource);
        List<SaleOrder> saleOrderList = saleOrderDao.getAllStatusList();
        logger.info("service返回结果为 {}", JSON.toJSONString(saleOrderList));
        return saleOrderList;
    }

}
