package cn.com.chinalife.ecdata.service.combine;

import cn.com.chinalife.ecdata.entity.combine.AnalysisIndex;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
public interface CombinationAnalysisService {

    List<AnalysisIndex> getRegisterAndActiveIndexOfDate();

    List<AnalysisIndex> getRegisterAndActiveIndexOfMonth();
}
