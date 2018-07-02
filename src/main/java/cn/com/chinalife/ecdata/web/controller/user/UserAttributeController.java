package cn.com.chinalife.ecdata.web.controller.user;

import cn.com.chinalife.ecdata.entity.ResponseBean;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.UserAttribute;
import cn.com.chinalife.ecdata.entity.user.UserSource;
import cn.com.chinalife.ecdata.service.InitService;
import cn.com.chinalife.ecdata.service.user.UserAttributeService;
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
@RequestMapping("/userAttribute")
public class UserAttributeController {
    private final Logger logger = LoggerFactory.getLogger(UserAttributeController.class);
    @Autowired
    UserAttributeService userAttributeService;
    @Autowired
    InitService initService;

    @RequestMapping("/summary")
    public String queryUserAttributeSummary(Model model) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(null));
        ResponseBean responseBean = new ResponseBean();
        try {
            QueryPara queryPara = new QueryPara();
            queryPara.setStartDate(DateUtils.getYesterday());
            queryPara.setEndDate(DateUtils.getYesterday());
            List<List<UserAttribute>> lists = new ArrayList<List<UserAttribute>>();
            List<UserAttribute> userSexList = userAttributeService.getUserSexList(queryPara);
            lists.add(userSexList);
            List<UserAttribute> userRank = userAttributeService.getUserRankList(queryPara);
            lists.add(userRank);
            List<UserAttribute> userAgeList = userAttributeService.getUserAgeList(queryPara);
            lists.add(userAgeList);
            List<UserSource> userSourceList = initService.getALLNewUserSource();
            model.addAttribute("userAttributeList", JSON.toJSONString(lists));
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
            return "attribute/userAttributeSummary";
        }

    }

    @RequestMapping(value = "/numQuery", produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
    public String queryUserAttributeNum(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            List<List<UserAttribute>> lists = new ArrayList<List<UserAttribute>>();
            List<UserAttribute> userSexList = userAttributeService.getUserSexList(queryPara);
            lists.add(userSexList);
            List<UserAttribute> userRank = userAttributeService.getUserRankList(queryPara);
            lists.add(userRank);
            List<UserAttribute> userAgeList = userAttributeService.getUserAgeList(queryPara);
            lists.add(userAgeList);
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
