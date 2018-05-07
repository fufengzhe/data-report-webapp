package cn.com.chinalife.ecdata.dao.sqlDao.combine;

import cn.com.chinalife.ecdata.entity.combine.AnalysisIndex;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
@Repository
public interface CombinationAnalysisDao {

    List<AnalysisIndex> getRegisterAndActiveIndexOfDate(QueryPara queryPara);

    List<AnalysisIndex> getRegisterListOfMonth(QueryPara queryPara);

    List<AnalysisIndex> getActiveListOfMonth(QueryPara queryPara);
}
