package cn.com.chinalife.ecdata.service.trade;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.trade.Premium;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
public interface PropertyPremiumService {
    List<Premium> getPropertyPremiumOverview();

    List<Premium> getPropertyPremiumDetail(QueryPara queryPara);

    int deleteAllExistedRecord(List<String> statIndexNameListOfPropertyPremium);

    int updatePropertyPremium(QueryPara queryPara);

    List<List<Premium>> getPreimumSummaryList(QueryPara queryPara, List<String> dateList);

    List<Premium> queryPropertyPremiumNum(QueryPara queryPara);
}
