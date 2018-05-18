package cn.com.chinalife.ecdata.web.controller.location;

import cn.com.chinalife.ecdata.entity.ResponseBean;
import cn.com.chinalife.ecdata.entity.combine.AnalysisIndex;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.service.location.LocationAnalysisService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.DateUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/2/28.
 */
@Controller
@RequestMapping("/locationAnalysis")
public class LocationAnalysisController {
    private final Logger logger = LoggerFactory.getLogger(LocationAnalysisController.class);
    @Autowired
    LocationAnalysisService locationAnalysisService;


    @RequestMapping("/registerMobile")
    public String registerMobile(Model model) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(null));
        ResponseBean responseBean = new ResponseBean();
        try {
            QueryPara queryPara = new QueryPara();
            queryPara.setStartDate(DateUtils.getYesterday());
            queryPara.setEndDate(DateUtils.getYesterday());
            List<List<AnalysisIndex>> analysisIndexList = locationAnalysisService.getRegisterMobileDistributeInfo(queryPara);
            List<AnalysisIndex> userSourceList = locationAnalysisService.getUserSourceList();
            model.addAttribute("analysisIndexList", JSON.toJSONString(analysisIndexList));
            model.addAttribute("userSourceList", JSON.toJSONString(userSourceList));
            model.addAttribute("startDate", queryPara.getStartDate());
            model.addAttribute("endDate", queryPara.getEndDate());
            model.addAttribute("jsVersion", CommonConstant.jsVersion);
            responseBean.setDetailInfo(model);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return "location/registerMobile";
        }
    }

    @RequestMapping(value = "/registerMobileNumQuery", produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
    public String queryRegisterMobileDis(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            List<List<AnalysisIndex>> analysisIndexList = locationAnalysisService.getRegisterMobileDistributeInfo(queryPara);
            responseBean.setDetailInfo(analysisIndexList);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return JSON.toJSONString(responseBean);
        }
    }

    @RequestMapping("/activeIP")
    public String activeIP(Model model) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(null));
        ResponseBean responseBean = new ResponseBean();
        try {
            QueryPara queryPara = new QueryPara();
            queryPara.setStartDate(DateUtils.getYesterday());
            queryPara.setEndDate(DateUtils.getYesterday());
            List<List<AnalysisIndex>> analysisIndexList = locationAnalysisService.getActiveIPDistributeInfo(queryPara);
            List<AnalysisIndex> userSourceList = locationAnalysisService.getUserSourceList();
            model.addAttribute("analysisIndexList", JSON.toJSONString(analysisIndexList));
            model.addAttribute("userSourceList", JSON.toJSONString(userSourceList));
            model.addAttribute("startDate", queryPara.getStartDate());
            model.addAttribute("endDate", queryPara.getEndDate());
            model.addAttribute("jsVersion", CommonConstant.jsVersion);
            responseBean.setDetailInfo(model);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return "location/activeIP";
        }
    }

    @RequestMapping(value = "/activeIPNumQuery", produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
    public String queryActiveIPDis(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            List<List<AnalysisIndex>> analysisIndexList = locationAnalysisService.getActiveIPDistributeInfo(queryPara);
            responseBean.setDetailInfo(analysisIndexList);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return JSON.toJSONString(responseBean);
        }
    }

    @RequestMapping("/activeHourAndUserCollDis")
    public String activeHourAndUserCollDis(Model model) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(null));
        ResponseBean responseBean = new ResponseBean();
        try {
            QueryPara queryPara = new QueryPara();
            queryPara.setStartDate(DateUtils.getYesterday());
            queryPara.setEndDate(DateUtils.getYesterday());
            List<List<AnalysisIndex>> analysisIndexList = locationAnalysisService.getActiveHourAndUserCollDisInfo(queryPara);
            List<AnalysisIndex> userSourceList = locationAnalysisService.getUserSourceList();
            model.addAttribute("analysisIndexList", JSON.toJSONString(analysisIndexList));
            model.addAttribute("userSourceList", JSON.toJSONString(userSourceList));
            model.addAttribute("startDate", queryPara.getStartDate());
            model.addAttribute("endDate", queryPara.getEndDate());
            model.addAttribute("jsVersion", CommonConstant.jsVersion);
            responseBean.setDetailInfo(model);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return "location/activeHourAndUserCollDis";
        }
    }

    @RequestMapping(value = "/activeHourAndUserCollDisNumQuery", produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
    public String queryActiveHourAndUserCollDis(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            List<List<AnalysisIndex>> analysisIndexList = locationAnalysisService.getActiveHourAndUserCollDisInfo(queryPara);
            responseBean.setDetailInfo(analysisIndexList);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return JSON.toJSONString(responseBean);
        }
    }


}
