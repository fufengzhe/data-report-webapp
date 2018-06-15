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
            queryPara.setStartDate(DateUtils.getYesterday());
            queryPara.setEndDate(DateUtils.getYesterday());
            List<UserRetention> userRetentionList = userRetentionService.getUserRetentionList(queryPara);
            List<UserSource> userSourceList = initService.getNewUserSource();
            model.addAttribute("userRetentionList", JSON.toJSONString(userRetentionList));
            model.addAttribute("userSourceList", JSON.toJSONString(userSourceList));
            model.addAttribute("startDate", DateUtils.getYesterday());
            model.addAttribute("endDate", DateUtils.getYesterday());
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
}
