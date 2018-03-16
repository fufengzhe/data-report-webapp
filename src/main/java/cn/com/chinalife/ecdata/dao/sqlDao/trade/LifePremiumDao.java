package cn.com.chinalife.ecdata.dao.sqlDao.trade;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.trade.Order;
import cn.com.chinalife.ecdata.entity.trade.Premium;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
@Repository
public interface LifePremiumDao {
    Premium getLifePremiumOverview();

    List<Order> getPremiumDetailWithOnlyAgentAndPolicyNo(QueryPara queryPara);

    List<Order> getPremiumDetailWithOnlyPolicyNoAndPremium(String policyNoFilterStr);
}
