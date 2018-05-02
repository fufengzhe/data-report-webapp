package cn.com.chinalife.ecdata.web.controller.combine;

import cn.com.chinalife.ecdata.entity.ResponseBean;
import cn.com.chinalife.ecdata.entity.combine.AnalysisIndex;
import cn.com.chinalife.ecdata.service.combine.CombinationAnalysisService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiexiangyu on 2018/2/28.
 */
@Controller
@RequestMapping("/combinationAnalysis")
public class CombinationAnalysisController {
    private final Logger logger = LoggerFactory.getLogger(CombinationAnalysisController.class);
    @Autowired
    CombinationAnalysisService combinationAnalysisService;


    @RequestMapping("/registerAndActive")
    public String registerAndActive(Model model) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(null));
        ResponseBean responseBean = new ResponseBean();
        try {
            List<List<AnalysisIndex>> analysisIndexList = new ArrayList<List<AnalysisIndex>>();
            List<AnalysisIndex> analysisIndexListOfDate = combinationAnalysisService.getRegisterAndActiveIndexOfDate();
            analysisIndexList.add(analysisIndexListOfDate);
            List<AnalysisIndex> analysisIndexListOfMonth = combinationAnalysisService.getRegisterAndActiveIndexOfMonth();
            analysisIndexList.add(analysisIndexListOfMonth);
            model.addAttribute("analysisIndexList", JSON.toJSONString(analysisIndexList));
            model.addAttribute("jsVersion", CommonConstant.jsVersion);
            responseBean.setDetailInfo(model);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return "combine/registerAndActive";
        }
    }

}
