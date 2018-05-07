package cn.com.chinalife.ecdata.web.controller.user;

import cn.com.chinalife.ecdata.entity.ResponseBean;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.ActiveUser;
import cn.com.chinalife.ecdata.service.user.ActiveUserService;
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
@RequestMapping("/activeUser")
public class ActiveUserController {
    private final Logger logger = LoggerFactory.getLogger(ActiveUserController.class);
    @Autowired
    ActiveUserService activeUserService;

    @RequestMapping(value = "/numQuery", produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
    public String queryActiveUserNum(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            if (queryPara.getUserSource() != null) {
                ActiveUser activeUser = activeUserService.getActiveUserNum(queryPara);
                responseBean.setDetailInfo(activeUser);
            } else {
                if (queryPara.getTimeSpan() == null) {
                    queryPara.setTimeSpan(CommonConstant.statTimeSpanOfDate);
                }
                List<ActiveUser> activeUserList = activeUserService.getActiveUserNumOfAllSourcesFromStatResult(queryPara);
                responseBean.setDetailInfo(activeUserList);
            }
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return JSON.toJSONString(responseBean);
        }
    }

    @RequestMapping("/detailQuery")
    @ResponseBody
    public String queryActiveUserDetail(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            List<String> oldUserIds = activeUserService.getActiveUserDetail(queryPara);
            responseBean.setDetailInfo(oldUserIds);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return JSON.toJSONString(responseBean);
        }
    }

    @RequestMapping("/summary")
    public String queryActiveUserSummary(Model model) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(null));
        ResponseBean responseBean = new ResponseBean();
        try {
            List<List<ActiveUser>> activeUserSummaryList = activeUserService.getActiveUserSummaryList();
            model.addAttribute("activeUserSummaryList", JSON.toJSONString(activeUserSummaryList));
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
            return "active/activeUserSummary";
        }

    }
}
