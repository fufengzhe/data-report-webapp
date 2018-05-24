package cn.com.chinalife.ecdata.dao.sqlDao.location;

import cn.com.chinalife.ecdata.entity.combine.AnalysisIndex;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
@Repository
public interface LocationAnalysisDao {

    List<AnalysisIndex> getRegisterMobileAndSourceList(QueryPara registerDate);

    int updateDistributeInfo(List<AnalysisIndex> distributeInfo);

    List<AnalysisIndex> getRegisterMobileDistributeInfo(QueryPara queryPara);

    List<AnalysisIndex> getUserSourceList();

    List<AnalysisIndex> getActiveIPDistributeInfo(QueryPara queryPara);

    List<AnalysisIndex> getActiveHourDisInfo(QueryPara queryPara);

    List<AnalysisIndex> getUserCollectionDisInfo(QueryPara queryPara);

    List<AnalysisIndex> getMigrateCollectionDis(QueryPara queryPara);

    List<AnalysisIndex> getMigrateCollectionFromDis(QueryPara queryPara);

    List<AnalysisIndex> getMigrateCollectionToDis(QueryPara queryPara);

    List<AnalysisIndex> getMigrateCollectionUserNumDis(QueryPara queryPara);
}
