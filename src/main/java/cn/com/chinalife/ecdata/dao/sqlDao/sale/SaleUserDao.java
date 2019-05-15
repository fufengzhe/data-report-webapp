package cn.com.chinalife.ecdata.dao.sqlDao.sale;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.sale.SaleUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/2.
 */
@Repository
public interface SaleUserDao {

    List<SaleUser> getSaleUserListOfSource(QueryPara queryPara);

    List<SaleUser> getSaleUserListOfHour(QueryPara queryPara);

    List<SaleUser> getSaleUserListForTrendOfDate(QueryPara queryPara);

    List<SaleUser> getSaleLogUserListOfSource(QueryPara queryPara);

    List<SaleUser> getSaleLogUserListOfMode(QueryPara queryPara);

    List<SaleUser> getSaleLogUserListForTrendOfDate(QueryPara queryPara);
}
