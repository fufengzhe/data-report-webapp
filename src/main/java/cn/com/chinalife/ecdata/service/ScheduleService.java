package cn.com.chinalife.ecdata.service;

import cn.com.chinalife.ecdata.entity.query.QueryPara;

/**
 * Created by xiexiangyu on 2018/3/6.
 */
public interface ScheduleService {

    int updateRegister(QueryPara queryPara);

    int updateActive(QueryPara queryPara);

    int updateLifePremium(QueryPara queryPara);

    int updatePropertyPremium(QueryPara queryPara);
}
