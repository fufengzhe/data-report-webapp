package cn.com.chinalife.ecdata.web.controller;

import cn.com.chinalife.ecdata.entity.ResponseBean;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.ActiveUser;
import cn.com.chinalife.ecdata.service.ScheduleService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    /**
     * 全量更新历史所有的数据，因为存在批退批改的情况，所以全量更新
     *
     * @param queryPara
     * @return
     */
    @RequestMapping("/updatePropertyPremium")
    @ResponseBody
    public String updatePropertyPremium(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            int effectedRowsNum = scheduleService.updatePropertyPremium(queryPara);
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
     * @param queryPara
     * @return
     */
    @RequestMapping("/sendOfficialSiteActiveNum")
    @ResponseBody
    public String sendOfficialSiteActiveNum(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            List<List<ActiveUser>> activeUserList = scheduleService.queryOfficialSiteActiveNum(queryPara);
            responseBean.setDetailInfo(activeUserList);
            scheduleService.sendOfficialSiteActiveNum(queryPara, activeUserList);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return JSON.toJSONString(responseBean);
        }
    }

    /**
     * @param queryPara
     * @return
     */
    @RequestMapping("/updateDistribute")
    @ResponseBody
    public String updateDistribute(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            int effectedRowsNum = scheduleService.updateDistribute(queryPara);
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
     * @param queryPara
     * @return
     */
    @RequestMapping("/updateActiveTimeDis")
    @ResponseBody
    public String updateActiveTimeDis(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            int effectedRowsNum = scheduleService.updateActiveTimeDis(queryPara);
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

    @RequestMapping("/updateUserCollectionInvokeDis")
    @ResponseBody
    public String updateUserCollectionInvokeDis(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            int effectedRowsNum = scheduleService.updateUserCollectionInvokeDis(queryPara);
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
     * @param queryPara
     * @return
     */
    @RequestMapping("/updateMigrateCollection")
    @ResponseBody
    public String updateMigrateCollection(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            int effectedRowsNum = scheduleService.updateMigrateCollection(queryPara);
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
     * @param queryPara
     * @return
     */
    @RequestMapping("/updateUserShare")
    @ResponseBody
    public String updateUserShare(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            int effectedRowsNum = scheduleService.updateUserShare(queryPara);
            responseBean.setDetailInfo(new StringBuilder("更新行数为").append(effectedRowsNum).toString());
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
     * @param queryPara
     * @return
     */
    @RequestMapping("/updateUserRetention")
    @ResponseBody
    public String updateUserRetention(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            int effectedRowsNum = scheduleService.updateUserRetention(queryPara);
            responseBean.setDetailInfo(new StringBuilder("更新行数为").append(effectedRowsNum).toString());
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
     * @param queryPara
     * @return
     */
    @RequestMapping("/updateUserAttribute")
    @ResponseBody
    public String updateUserAttribute(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            int effectedRowsNum = scheduleService.updateUserAttribute(queryPara, false);
            responseBean.setDetailInfo(new StringBuilder("更新行数为").append(effectedRowsNum).toString());
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
