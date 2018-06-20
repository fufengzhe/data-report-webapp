package cn.com.chinalife.ecdata.service.location;

import cn.com.chinalife.ecdata.entity.combine.AnalysisIndex;
import cn.com.chinalife.ecdata.entity.query.QueryPara;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
public interface LocationAnalysisService {

    int updateRegisterMobileDistribute(QueryPara queryPara) throws Exception;

    int updateActiveIPDistribute(QueryPara queryPara) throws Exception;

    List<List<AnalysisIndex>> getRegisterMobileDistributeInfo(QueryPara queryPara);

    List<AnalysisIndex> getUserSourceList();

    List<List<AnalysisIndex>> getActiveIPDistributeInfo(QueryPara queryPara);

    int updateActiveTimeDis(QueryPara queryPara);

    int updateUserCollectionInvokeDis(QueryPara queryPara);

    List<List<AnalysisIndex>> getActiveHourAndUserCollDisInfo(QueryPara queryPara);

    int updateMigrateCollection(QueryPara queryPara);

    List<List<AnalysisIndex>> getMigrateCollectionDis(QueryPara queryPara);
}
