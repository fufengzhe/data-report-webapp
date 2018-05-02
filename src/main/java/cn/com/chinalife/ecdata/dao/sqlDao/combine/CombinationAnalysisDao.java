package cn.com.chinalife.ecdata.dao.sqlDao.combine;

import cn.com.chinalife.ecdata.entity.combine.AnalysisIndex;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
@Repository
public interface CombinationAnalysisDao {

    List<AnalysisIndex> getRegisterAndActiveIndexOfDate();

    List<AnalysisIndex> getRegisterListOfMonth();

    List<AnalysisIndex> getActiveListOfMonth();
}
