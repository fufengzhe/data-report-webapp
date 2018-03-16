package cn.com.chinalife.ecdata.service.trade;

import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.trade.Premium;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
public interface LifePremiumService {
    Premium getLifePremiumOverview();

    List<Premium> getLifePremiumDetail(QueryPara queryPara);
}
