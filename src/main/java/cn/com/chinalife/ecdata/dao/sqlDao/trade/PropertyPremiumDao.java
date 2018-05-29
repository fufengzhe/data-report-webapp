package cn.com.chinalife.ecdata.dao.sqlDao.trade;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.trade.Branch;
import cn.com.chinalife.ecdata.entity.trade.Order;
import cn.com.chinalife.ecdata.entity.trade.Premium;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
@Repository
public interface PropertyPremiumDao {
    List<Premium> getPropertyPremiumOverview(List<String> statIndexNameListOfPropertyPremium);

    List<Order> getPremiumDetailWithoutReverseAndCorrect(QueryPara queryPara);

    List<Branch> getBranchList();

    List<Order> getReverseAndCorrectOrderList(QueryPara queryPara);

    int deleteAllExistedRecord(List<String> statIndexNameListOfPropertyPremium);

    List<Premium> getPremiumDetailListOfDX(QueryPara queryPara);

    List<Premium> getPremiumDetailListOfJQ(QueryPara queryPara);

    List<Premium> getPremiumDetailListOfPTPG(QueryPara queryPara);

    List<Premium> getPremiumDetailListOfInternet(QueryPara queryPara);

    int updatePropertyPremium(List<Premium> premiumList);

    List<Premium> getPropertyPremiumDetail(QueryPara queryPara);

    List<Premium> getPropertyPremiumDisInfoList(QueryPara queryPara);

    List<Premium> getPropertyPremiumCompleteRatioInfo(QueryPara queryPara);

    List<Premium> getPropertyDateTrendInfo(QueryPara queryPara);
}
