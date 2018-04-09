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

    int deleteAllExistedRecord(String indexName);

    List<Premium> getLifePremiumDetail(QueryPara queryPara);

    int updateLifePremium(List<Premium> premiumList);

    List<Premium> getLifePremiumDetailWithoutDistinctBranch(QueryPara queryPara);

    List<Premium> getLifePremiumDetailOfInternet(QueryPara queryPara);

    List<Premium> getLifePremiumOfUpper();

    List<Premium> getLifePremiumOfLower();

    List<Premium> getLifePremiumDetailFromStatResult(QueryPara queryPara);
}
