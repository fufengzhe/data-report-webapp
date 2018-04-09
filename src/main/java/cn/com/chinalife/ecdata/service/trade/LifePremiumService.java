package cn.com.chinalife.ecdata.service.trade;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.trade.Premium;

import java.text.ParseException;
import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
public interface LifePremiumService {
    List<Premium> getLifePremiumOverview() throws ParseException;

    List<Premium> getLifePremiumDetail(QueryPara queryPara);

    int updateLifePremium(List<Premium> premiumList);

    int deleteAllExistedRecord(String indexName);

    List<Premium> getLifePremiumDetailFromStatResult(QueryPara queryPara);
}
