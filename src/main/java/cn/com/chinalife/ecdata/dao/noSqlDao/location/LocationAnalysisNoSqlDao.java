package cn.com.chinalife.ecdata.dao.noSqlDao.location;

import cn.com.chinalife.ecdata.entity.combine.AnalysisIndex;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
@Repository
public interface LocationAnalysisNoSqlDao {


    List<AnalysisIndex> getActiveIPAndSourceList(QueryPara para);

    List<AnalysisIndex> getDistinctActiveIPAndSourceList(QueryPara para);

    List<AnalysisIndex> getActiveTimeDis(QueryPara queryPara);

    List<AnalysisIndex> getUserCollectionInvokeDis(QueryPara queryPara);
}
