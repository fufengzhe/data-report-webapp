package cn.com.chinalife.ecdata.web.controller.userShare;

import cn.com.chinalife.ecdata.entity.ResponseBean;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.userShare.UserShare;
import cn.com.chinalife.ecdata.service.userShare.UserShareService;
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
@RequestMapping("/userShare")
public class UserShareController {
    private final Logger logger = LoggerFactory.getLogger(UserShareController.class);
    @Autowired
    UserShareService userShareService;


    @RequestMapping("/distribute")
    public String registerAndActive(Model model) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(null));
        ResponseBean responseBean = new ResponseBean();
        try {
            QueryPara queryPara = new QueryPara();
            queryPara.setStartDate(DateUtils.getYesterday());
            queryPara.setEndDate(DateUtils.getYesterday());
            List<List<UserShare>> userShareList = new ArrayList<List<UserShare>>();
            List<UserShare> userSourceDisList = userShareService.getUserSourceDisList(queryPara);
            userShareList.add(userSourceDisList);
            List<UserShare> hourDisList = userShareService.getHourDisList(queryPara);
            userShareList.add(hourDisList);
            List<UserShare> dateTrendList = userShareService.getDateTrend(queryPara);
            userShareList.add(dateTrendList);
            model.addAttribute("userShareList", JSON.toJSONString(userShareList));
            List<UserShare> userSourceList = userShareService.getUserSourceList();
            model.addAttribute("userSourceList", JSON.toJSONString(userSourceList));
            model.addAttribute("startDate", DateUtils.getYesterday());
            model.addAttribute("endDate", DateUtils.getYesterday());
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
            return "userShare/numDistribute";
        }
    }

    @RequestMapping(value = "/numQuery", produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
    public String queryUserShareNum(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            List<List<UserShare>> userShareList = new ArrayList<List<UserShare>>();
            List<UserShare> userSourceDisList=userShareService.getUserSourceDisList(queryPara);
            userShareList.add(userSourceDisList);
            List<UserShare> hourDisList=userShareService.getHourDisList(queryPara);
            userShareList.add(hourDisList);
            responseBean.setDetailInfo(userShareList);
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
