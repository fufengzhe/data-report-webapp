package cn.com.chinalife.ecdata.web.controller;

import cn.com.chinalife.ecdata.entity.ResponseBean;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.service.ScheduleService;
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
@RequestMapping("/schedule")
public class ScheduleController {
    private final Logger logger = LoggerFactory.getLogger(ScheduleController.class);
    @Autowired
    ScheduleService scheduleService;

    @RequestMapping("/updateRegister")
    @ResponseBody
    public String updateRegister(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            int effectedRowsNum = scheduleService.updateRegister(queryPara);
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

    @RequestMapping("/updateActive")
    @ResponseBody
    public String updateActive(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            if (queryPara.getTimeSpan() == null) {
                queryPara.setTimeSpan(CommonConstant.statTimeSpanOfDate);
            }
            int effectedRowsNum = scheduleService.updateActive(queryPara);
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

    /**
     * 全量更新历史所有的数据，天，月，年维度，因为存在退保的情况，所以全量更新
     *
     * @param queryPara
     * @return
     */
    @RequestMapping("/updateLifePremium")
    @ResponseBody
    public String updateLifePremium(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            int effectedRowsNum = scheduleService.updateLifePremium(queryPara);
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
