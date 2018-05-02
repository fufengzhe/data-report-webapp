package cn.com.chinalife.ecdata.web.controller.user;

import cn.com.chinalife.ecdata.entity.ResponseBean;
import cn.com.chinalife.ecdata.entity.query.QueryPara;
import cn.com.chinalife.ecdata.entity.user.RegisterUser;
import cn.com.chinalife.ecdata.service.user.RegisterUserService;
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
@RequestMapping("/registerUser")
public class RegisterUserController {
    private final Logger logger = LoggerFactory.getLogger(RegisterUserController.class);
    @Autowired
    RegisterUserService registerUserService;

    @RequestMapping("/numQuery")
    @ResponseBody
    public String queryRegisterUserNum(QueryPara queryPara) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(queryPara));
        ResponseBean responseBean = new ResponseBean();
        try {
            if (queryPara.getUserSource() != null) {
                RegisterUser registerUser = registerUserService.getRegisterUserNum(queryPara);
                responseBean.setDetailInfo(registerUser);
            } else {
                List<RegisterUser> registerUserList = registerUserService.getRegisterUserNumOfAllSourcesFromStatResult(queryPara);
                responseBean.setDetailInfo(registerUserList);
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

    @RequestMapping("/numOverview")
    @ResponseBody
    public String queryNumOverview() {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(null));
        ResponseBean responseBean = new ResponseBean();
        try {
            List<RegisterUser> registerUserList = registerUserService.getRegisterNumOverview();
            responseBean.setDetailInfo(registerUserList);
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
    public String queryRegisterUserSummary(Model model) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(null));
        ResponseBean responseBean = new ResponseBean();
        try {
            List<List<RegisterUser>> registerUserList = registerUserService.getRegisterUserSummaryList();
            model.addAttribute("registerUserList", JSON.toJSONString(registerUserList));
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
            return "register/registerUserSummary";
        }

    }

}
