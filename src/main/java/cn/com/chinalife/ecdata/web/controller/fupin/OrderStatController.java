package cn.com.chinalife.ecdata.web.controller.fupin;

import cn.com.chinalife.ecdata.entity.ResponseBean;
import cn.com.chinalife.ecdata.entity.fupin.OrderStat;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.service.fupin.OrderStatService;
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
 * Created by xiexiangyu on 2018/3/2.
 */
@Controller
@RequestMapping("/orderStat")
public class OrderStatController {
    private final Logger logger = LoggerFactory.getLogger(OrderStatController.class);
    @Autowired
    OrderStatService orderStatService;

    @RequestMapping("/summary")
    public String queryOrderStatSummary(Model model) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(null));
        ResponseBean responseBean = new ResponseBean();
        try {
            List<List<OrderStat>> orderStatList = orderStatService.getOrderStatList();
            model.addAttribute("orderStatList", JSON.toJSONString(orderStatList));
            List<OrderStat> orderProductList = orderStatService.getOrderProductList();
            model.addAttribute("orderProductList", JSON.toJSONString(orderProductList));
            List<String> dateList = DateUtils.getDateList(DateUtils.getBeforeXDay(7), DateUtils.getBeforeXDay(1));
            model.addAttribute("startDate", DateUtils.getYesterday());
            model.addAttribute("endDate", DateUtils.getYesterday());
            model.addAttribute("dates", JSON.toJSONString(dateList));
            model.addAttribute("jsVersion", CommonConstant.jsVersion);
            responseBean.setDetailInfo(model);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return "fupin/orderStatSummary";
        }
    }

    @RequestMapping("/IPSummary")
    public String queryOrderStatIPSummary(Model model) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(null));
        ResponseBean responseBean = new ResponseBean();
        try {
            QueryPara queryPara = new QueryPara();
            queryPara.setStartDate(DateUtils.getYesterday());
            queryPara.setEndDate(DateUtils.getYesterday());
            List<List<OrderStat>> orderStatIPDistributeList = orderStatService.getOrderStatIPDistributeList(queryPara);
            model.addAttribute("orderStatList", JSON.toJSONString(orderStatIPDistributeList));
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
            return "fupin/orderStatIPSummary";
        }
    }

    @RequestMapping(value = "/numQuery", produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
    public String queryOrderStatNum(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            List<OrderStat> orderStatList = orderStatService.getOrderStatListForTimeSpanFromStatTable(queryPara);
            responseBean.setDetailInfo(orderStatList);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return JSON.toJSONString(responseBean);
        }
    }


    @RequestMapping(value = "/IPNumQuery", produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
    public String queryOrderStatIPNum(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            List<List<OrderStat>> orderStatList = orderStatService.getOrderStatIPDistributeList(queryPara);
            responseBean.setDetailInfo(orderStatList);
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
