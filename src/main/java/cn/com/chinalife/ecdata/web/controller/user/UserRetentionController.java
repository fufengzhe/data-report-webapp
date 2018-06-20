package cn.com.chinalife.ecdata.web.controller.user;

import cn.com.chinalife.ecdata.entity.ResponseBean;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.UserRetention;
import cn.com.chinalife.ecdata.entity.user.UserSource;
import cn.com.chinalife.ecdata.service.InitService;
import cn.com.chinalife.ecdata.service.user.UserRetentionService;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiexiangyu on 2018/2/28.
 */
@Controller
@RequestMapping("/userRetention")
public class UserRetentionController {
    private final Logger logger = LoggerFactory.getLogger(UserRetentionController.class);
    @Autowired
    UserRetentionService userRetentionService;
    @Autowired
    InitService initService;

    @RequestMapping("/summary")
    public String queryUserRetentionSummary(Model model) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(null));
        ResponseBean responseBean = new ResponseBean();
        try {
            QueryPara queryPara = new QueryPara();
            queryPara.setStartDate(DateUtils.getBeforeXDayBasedGivenDate(DateUtils.getYesterday(), 30));
            queryPara.setEndDate(DateUtils.getBeforeXDayBasedGivenDate(DateUtils.getYesterday(), 1));
            model.addAttribute("endDate", queryPara.getEndDate());
            queryPara.setUserSource("掌上国寿");
            List<List<UserRetention>> lists = new ArrayList<List<UserRetention>>();
            List<UserRetention> userRetentionList = userRetentionService.getUserRetentionList(queryPara);
            lists.add(userRetentionList);
            List<UserRetention> userRetentionListForTrendChart = userRetentionService.getUserRetentionListForTrendChart(userRetentionList, false);
            lists.add(userRetentionListForTrendChart);
            queryPara.setUserSource(null);
            queryPara.setEndDate(DateUtils.getBeforeXDayBasedGivenDate(DateUtils.getYesterday(), 30));
            List<UserRetention> userRetentionListUserSourceDimension = userRetentionService.getUserRetentionList(queryPara);
            lists.add(userRetentionListUserSourceDimension);
            List<UserRetention> userRetentionListForTrendChartUserSourceDimension = userRetentionService.getUserRetentionListForTrendChart(userRetentionListUserSourceDimension, true);
            lists.add(userRetentionListForTrendChartUserSourceDimension);
            List<UserSource> userSourceList = initService.getNewUserSource();
            model.addAttribute("userRetentionList", JSON.toJSONString(lists));
            model.addAttribute("userSourceList", JSON.toJSONString(userSourceList));
            model.addAttribute("startDate", queryPara.getStartDate());
            List<String> dateList = userRetentionService.getxAxisLabel();
            model.addAttribute("dates", JSON.toJSONString(dateList));
            model.addAttribute("jsVersion", CommonConstant.jsVersion);
            responseBean.setDetailInfo(model);
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.queryFailureStr);
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return "retention/userRetentionSummary";
        }

    }

    @RequestMapping(value = "/numQuery", produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
    public String queryUserRetentionNum(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            List<List<UserRetention>> lists = new ArrayList<List<UserRetention>>();
            List<UserRetention> userRetentionList = userRetentionService.getUserRetentionList(queryPara);
            lists.add(userRetentionList);
            List<UserRetention> userRetentionListForTrendChart = userRetentionService.getUserRetentionListForTrendChart(userRetentionList, "1".equals(queryPara.getQueryType()) ? false : true);
            lists.add(userRetentionListForTrendChart);
            responseBean.setDetailInfo(lists);
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
