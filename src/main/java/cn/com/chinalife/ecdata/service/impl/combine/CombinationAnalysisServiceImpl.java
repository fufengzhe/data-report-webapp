package cn.com.chinalife.ecdata.service.impl.combine;

import cn.com.chinalife.ecdata.dao.sqlDao.combine.CombinationAnalysisDao;
import cn.com.chinalife.ecdata.entity.combine.AnalysisIndex;
import cn.com.chinalife.ecdata.service.combine.CombinationAnalysisService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.DataSourceContextHolder;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
@Service
public class CombinationAnalysisServiceImpl implements CombinationAnalysisService {
    private final Logger logger = LoggerFactory.getLogger(CombinationAnalysisServiceImpl.class);
    @Autowired
    CombinationAnalysisDao combinationAnalysisDao;

    public List<AnalysisIndex> getRegisterAndActiveIndexOfDate() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<AnalysisIndex> analysisIndexList = combinationAnalysisDao.getRegisterAndActiveIndexOfDate();
        logger.info("service返回结果为 {}", JSON.toJSONString(analysisIndexList));
        return analysisIndexList;
    }

    public List<AnalysisIndex> getRegisterAndActiveIndexOfMonth() {
        logger.info("controller传入的参数为 {}", JSON.toJSONString(null));
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        List<AnalysisIndex> analysisIndexList = new ArrayList<AnalysisIndex>();
        //因注册和活跃在计算月维度时计算方式不同，活跃月维度之间涉及到去重逻辑，所以要分开计算
        List<AnalysisIndex> registerListOfMonth = combinationAnalysisDao.getRegisterListOfMonth();
        List<AnalysisIndex> activeListOfMonth = combinationAnalysisDao.getActiveListOfMonth();
        analysisIndexList = this.getMergedListUsingDateAndMonth(registerListOfMonth, activeListOfMonth);
        logger.info("service返回结果为 {}", JSON.toJSONString(analysisIndexList));
        return analysisIndexList;
    }

    private List<AnalysisIndex> getMergedListUsingDateAndMonth(List<AnalysisIndex> registerListOfMonth, List<AnalysisIndex> activeListOfMonth) {
        List<AnalysisIndex> analysisIndexList = new ArrayList<AnalysisIndex>();
        Set<String> sourceSet = new HashSet<String>();
        Map<String, Integer> registerMap = new HashMap<String, Integer>();
        Map<String, Integer> activeMap = new HashMap<String, Integer>();
        for (AnalysisIndex analysisIndex : registerListOfMonth) {
            registerMap.put(analysisIndex.getIndexSource(), analysisIndex.getRegisterNum());
            sourceSet.add(analysisIndex.getIndexSource());
        }
        for (AnalysisIndex analysisIndex : activeListOfMonth) {
            activeMap.put(analysisIndex.getIndexSource(), analysisIndex.getActiveNum());
            sourceSet.add(analysisIndex.getIndexSource());
        }
        for (String source : sourceSet) {
            AnalysisIndex analysisIndex = new AnalysisIndex();
            analysisIndex.setIndexSource(source);
            analysisIndex.setRegisterNum(registerMap.get(source) == null ? 0 : registerMap.get(source));
            analysisIndex.setActiveNum(activeMap.get(source) == null ? 0 : activeMap.get(source));
            analysisIndexList.add(analysisIndex);
        }
        return analysisIndexList;
    }
}
