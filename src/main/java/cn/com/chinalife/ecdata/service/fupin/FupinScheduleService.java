package cn.com.chinalife.ecdata.service.fupin;

import cn.com.chinalife.ecdata.entity.query.QueryPara;

/**
 * Created by xiexiangyu on 2018/3/6.
 */
public interface FupinScheduleService {
    int updatePageClick(QueryPara queryPara);

    int updateOrderStat(QueryPara queryPara);

    int updatePageClickIPInfo(QueryPara queryPara);
}
