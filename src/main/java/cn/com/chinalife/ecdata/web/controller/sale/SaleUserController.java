package cn.com.chinalife.ecdata.web.controller.sale;

import cn.com.chinalife.ecdata.entity.ResponseBean;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.sale.SaleUser;
import cn.com.chinalife.ecdata.service.sale.SaleUserService;
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
 * Created by xiexiangyu on 2019/5/8.
 */
@Controller
@RequestMapping("/saleUser")
public class SaleUserController {
    private final Logger logger = LoggerFactory.getLogger(SaleUserController.class);
    @Autowired
    SaleUserService saleUserService;

    @RequestMapping("/registerNumSummary")
    public String querySaleUserRegisterNumSummary(Model model) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(null));
        ResponseBean responseBean = new ResponseBean();
        try {
            QueryPara queryPara = new QueryPara();
            queryPara.setStartDate(DateUtils.getMonthBeginDateUsingYesterday(DateUtils.getYesterday()));
            queryPara.setEndDate(DateUtils.getYesterday());
            List<List<SaleUser>> saleUserRegisterNumList = saleUserService.getSaleUserRegisterNumList(queryPara);
            model.addAttribute("saleUserRegisterNumList", JSON.toJSONString(saleUserRegisterNumList));
            model.addAttribute("startDate", queryPara.getStartDate());
            model.addAttribute("endDate", queryPara.getEndDate());
            List<String> dateList = DateUtils.getDateList(DateUtils.getBeforeXDay(7), DateUtils.getBeforeXDay(1));
            model.addAttribute("dates", JSON.toJSONString(dateList));
            model.addAttribute("jsVersion", CommonConstant.jsVersion);
            responseBean.setDetailInfo(model);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return "sale/saleUserRegisterNumSummary";
        }
    }

    @RequestMapping(value = "/registerNumQuery", produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
    public String querySaleUserRegisterNum(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            List<List<SaleUser>> saleUserList = saleUserService.getSaleUserRegisterNumListOfSourceAndHour(queryPara);
            responseBean.setDetailInfo(saleUserList);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return JSON.toJSONString(responseBean);
        }
    }

    @RequestMapping("/logNumSummary")
    public String querySaleUserLogNumSummary(Model model) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(null));
        ResponseBean responseBean = new ResponseBean();
        try {
            QueryPara queryPara = new QueryPara();
            queryPara.setStartDate(DateUtils.getMonthBeginDateUsingYesterday(DateUtils.getYesterday()));
            queryPara.setEndDate(DateUtils.getYesterday());
            List<List<SaleUser>> saleUserLogNumList = saleUserService.getSaleUserLogNumList(queryPara);
            model.addAttribute("saleUserLogNumList", JSON.toJSONString(saleUserLogNumList));
            model.addAttribute("startDate", queryPara.getStartDate());
            model.addAttribute("endDate", queryPara.getEndDate());
            List<String> dateList = DateUtils.getDateList(DateUtils.getBeforeXDay(7), DateUtils.getBeforeXDay(1));
            model.addAttribute("dates", JSON.toJSONString(dateList));
            model.addAttribute("jsVersion", CommonConstant.jsVersion);
            responseBean.setDetailInfo(model);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return "sale/saleUserLogNumSummary";
        }
    }

    @RequestMapping(value = "/logNumQuery", produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
    public String querySaleUserLogNum(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            List<List<SaleUser>> saleUserList = saleUserService.getSaleUserLogNumListOfSourceAndMode(queryPara);
            responseBean.setDetailInfo(saleUserList);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return JSON.toJSONString(responseBean);
        }
    }

    @RequestMapping("/registerMobileSummary")
    public String querySaleUserRegisterMobileSummary(Model model) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(null));
        ResponseBean responseBean = new ResponseBean();
        try {
            QueryPara queryPara = new QueryPara();
            queryPara.setStartDate(DateUtils.getMonthBeginDateUsingYesterday(DateUtils.getYesterday()));
            queryPara.setEndDate(DateUtils.getYesterday());
            List<List<SaleUser>> saleUserLogNumList = null;
            model.addAttribute("saleUserLogNumList", JSON.toJSONString(saleUserLogNumList));
            model.addAttribute("startDate", queryPara.getStartDate());
            model.addAttribute("endDate", queryPara.getEndDate());
            List<String> dateList = DateUtils.getDateList(DateUtils.getBeforeXDay(7), DateUtils.getBeforeXDay(1));
            model.addAttribute("dates", JSON.toJSONString(dateList));
            model.addAttribute("jsVersion", CommonConstant.jsVersion);
            responseBean.setDetailInfo(model);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return "sale/saleUserRegisterMobileSummary";
        }
    }

}
