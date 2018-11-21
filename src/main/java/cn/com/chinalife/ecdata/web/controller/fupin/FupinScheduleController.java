package cn.com.chinalife.ecdata.web.controller.fupin;

import cn.com.chinalife.ecdata.entity.ResponseBean;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.service.fupin.FupinScheduleService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xiexiangyu on 2018/3/6.
 */
@Controller
@RequestMapping("/fupinSchedule")
public class FupinScheduleController {
    private final Logger logger = LoggerFactory.getLogger(FupinScheduleController.class);
    @Autowired
    FupinScheduleService fupinScheduleService;

    @RequestMapping("/updatePageClick")
    @ResponseBody
    public String updatePageClick(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            int effectedRowsNum = fupinScheduleService.updatePageClick(queryPara);
            responseBean.setDetailInfo("更新行数为" + effectedRowsNum);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.updateFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return JSON.toJSONString(responseBean);
        }
    }

    @RequestMapping("/updatePageClickIPInfo")
    @ResponseBody
    public String updatePageClickIPInfo(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            int effectedRowsNum = fupinScheduleService.updatePageClickIPInfo(queryPara);
            responseBean.setDetailInfo("更新行数为" + effectedRowsNum);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.updateFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return JSON.toJSONString(responseBean);
        }
    }

    @RequestMapping("/updateOrderStat")
    @ResponseBody
    public String updateOrderStat(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            int effectedRowsNum = fupinScheduleService.updateOrderStat(queryPara);
            responseBean.setDetailInfo("更新行数为" + effectedRowsNum);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.updateFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return JSON.toJSONString(responseBean);
        }
    }

    @RequestMapping("/updateOrderIPInfo")
    @ResponseBody
    public String updateOrderIPInfo(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            int effectedRowsNum = fupinScheduleService.updateOrderIPInfo(queryPara);
            responseBean.setDetailInfo("更新行数为" + effectedRowsNum);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.updateFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return JSON.toJSONString(responseBean);
        }
    }

    @RequestMapping("/updateOrderFromToAreaInfo")
    @ResponseBody
    public String updateOrderFromToAreaInfo(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            int effectedRowsNum = fupinScheduleService.updateOrderFromToAreaInfo(queryPara);
            responseBean.setDetailInfo("更新行数为" + effectedRowsNum);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.updateFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return JSON.toJSONString(responseBean);
        }
    }


}
