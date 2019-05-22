package cn.com.chinalife.ecdata.service.impl.sale;

import cn.com.chinalife.ecdata.dao.sqlDao.sale.SaleUserDao;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.sale.SaleUser;
import cn.com.chinalife.ecdata.service.sale.SaleUserService;
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
public class SaleUserServiceImpl implements SaleUserService {
    private final Logger logger = LoggerFactory.getLogger(SaleUserServiceImpl.class);
    @Autowired
    SaleUserDao saleUserDao;

    public List<List<SaleUser>> getSaleUserRegisterNumList(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        List<List<SaleUser>> lists = new ArrayList<List<SaleUser>>();
        DataSourceContextHolder.setDbType(CommonConstant.saleStatisticsDataSource);
        List<SaleUser> saleUserListOfSource = saleUserDao.getSaleUserListOfSource(queryPara);
        lists.add(saleUserListOfSource);
        List<SaleUser> saleUserListOfHour = saleUserDao.getSaleUserListOfHour(queryPara);
        lists.add(saleUserListOfHour);
        List<SaleUser> saleUserListForTrendOfDate = saleUserDao.getSaleUserListForTrendOfDate(queryPara);
        lists.add(saleUserListForTrendOfDate);
        logger.info("service返回结果为 {}", JSON.toJSONString(lists));
        return lists;
    }

    public List<List<SaleUser>> getSaleUserRegisterNumListOfSourceAndHour(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        List<List<SaleUser>> lists = new ArrayList<List<SaleUser>>();
        DataSourceContextHolder.setDbType(CommonConstant.saleStatisticsDataSource);
        List<SaleUser> saleUserListOfSource = saleUserDao.getSaleUserListOfSource(queryPara);
        lists.add(saleUserListOfSource);
        List<SaleUser> saleUserListOfHour = saleUserDao.getSaleUserListOfHour(queryPara);
        lists.add(saleUserListOfHour);
        logger.info("service返回结果为 {}", JSON.toJSONString(lists));
        return lists;
    }

    public List<List<SaleUser>> getSaleUserLogNumList(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        List<List<SaleUser>> lists = new ArrayList<List<SaleUser>>();
        DataSourceContextHolder.setDbType(CommonConstant.saleStatisticsDataSource);
        List<SaleUser> saleUserListOfSource = saleUserDao.getSaleLogUserListOfSource(queryPara);
        lists.add(saleUserListOfSource);
        List<SaleUser> saleUserListOfMode = saleUserDao.getSaleLogUserListOfMode(queryPara);
        lists.add(saleUserListOfMode);
        List<SaleUser> saleUserListForTrendOfDate = saleUserDao.getSaleLogUserListForTrendOfDate(queryPara);
        lists.add(saleUserListForTrendOfDate);
        logger.info("service返回结果为 {}", JSON.toJSONString(lists));
        return lists;
    }

    public List<List<SaleUser>> getSaleUserLogNumListOfSourceAndMode(QueryPara queryPara) {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(queryPara));
        List<List<SaleUser>> lists = new ArrayList<List<SaleUser>>();
        DataSourceContextHolder.setDbType(CommonConstant.saleStatisticsDataSource);
        List<SaleUser> saleUserListOfSource = saleUserDao.getSaleLogUserListOfSource(queryPara);
        lists.add(saleUserListOfSource);
        List<SaleUser> saleUserListOfMode = saleUserDao.getSaleLogUserListOfMode(queryPara);
        lists.add(saleUserListOfMode);
        logger.info("service返回结果为 {}", JSON.toJSONString(lists));
        return lists;
    }

}
