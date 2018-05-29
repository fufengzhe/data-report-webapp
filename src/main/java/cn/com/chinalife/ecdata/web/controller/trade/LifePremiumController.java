package cn.com.chinalife.ecdata.web.controller.trade;

import cn.com.chinalife.ecdata.entity.ResponseBean;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.trade.Premium;
import cn.com.chinalife.ecdata.service.trade.LifePremiumService;
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
@RequestMapping("/life")
public class LifePremiumController {
    private final Logger logger = LoggerFactory.getLogger(LifePremiumController.class);
    @Autowired
    LifePremiumService lifePremiumService;

    @RequestMapping("/premiumOverview")
    @ResponseBody
    public String queryPremiumOverview() {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(null));
        ResponseBean responseBean = new ResponseBean();
        try {
            List<Premium> premiumList = lifePremiumService.getLifePremiumOverview();
            responseBean.setDetailInfo(premiumList);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return JSON.toJSONString(responseBean);
        }
    }

    @RequestMapping("/premiumDetail")
    @ResponseBody
    public String queryPremiumDetail(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            List<Premium> premiumList = lifePremiumService.getLifePremiumDetailFromStatResult(queryPara);
            responseBean.setDetailInfo(premiumList);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return JSON.toJSONString(responseBean);
        }
    }

    @RequestMapping("/premiumSummary")
    public String queryPremiumSummary(Model model) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(null));
        ResponseBean responseBean = new ResponseBean();
        try {
            QueryPara queryPara = new QueryPara();
            queryPara.setStartDate(DateUtils.getYesterday());
            queryPara.setEndDate(DateUtils.getYesterday());
            model.addAttribute("startDate", queryPara.getStartDate());
            model.addAttribute("endDate", queryPara.getEndDate());
            List<String> dateList = DateUtils.getDateList(DateUtils.getBeforeXDay(7), DateUtils.getBeforeXDay(1));
            List<List<Premium>> premiumList = lifePremiumService.getPreimumSummaryList(queryPara, dateList);
            model.addAttribute("premiumList", JSON.toJSONString(premiumList));
            model.addAttribute("dates", JSON.toJSONString(dateList));
            model.addAttribute("jsVersion", CommonConstant.jsVersion);
            responseBean.setDetailInfo(model);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return "premium/lifePremiumSummary";
        }
    }

    @RequestMapping(value = "/numQuery", produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
    public String queryPremiumNum(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            List<Premium> premiumList = lifePremiumService.queryPremiumNum(queryPara);
            responseBean.setDetailInfo(premiumList);
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
