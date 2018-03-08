package cn.com.chinalife.ecdata.web.controller.user;

import cn.com.chinalife.ecdata.entity.ResponseBean;
import cn.com.chinalife.ecdata.entity.user.LogUser;
import cn.com.chinalife.ecdata.service.user.UserLogService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.CommonUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by xiexiangyu on 2018/3/7.
 */
@Controller
@RequestMapping("/user")
public class UserLogController {
    private final Logger logger = LoggerFactory.getLogger(UserLogController.class);
    @Autowired
    UserLogService userLogService;

    @RequestMapping("/login")
    @ResponseBody
    public String login(LogUser requestUser, HttpSession session, HttpServletResponse response) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(requestUser));
        ResponseBean responseBean = new ResponseBean();
        try {
            LogUser logUser = userLogService.login(requestUser);
            if (logUser != null) {
                Cookie username = new Cookie("username", logUser.getUsername());
                username.setMaxAge(Integer.MAX_VALUE);
                username.setPath("/");
                Cookie password = new Cookie("password", logUser.getPassword());
                password.setMaxAge(Integer.MAX_VALUE);
                password.setPath("/");
                response.addCookie(username);
                response.addCookie(password);
                session.setAttribute("user", logUser);
                responseBean.setDetailInfo(logUser);
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

    @RequestMapping("/logout")
    @ResponseBody
    public String logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        logger.info("前端传入的参数为 {}", JSON.toJSONString(null));
        ResponseBean responseBean = new ResponseBean();
        try {
            Cookie[] cookies = httpServletRequest.getCookies();
            int count = 0;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("username".equals(cookie.getName())) {
                        CommonUtils.setCookieInvalid(cookie);
                        httpServletResponse.addCookie(cookie);
                        count++;
                    }
                    if ("password".equals(cookie.getName())) {
                        CommonUtils.setCookieInvalid(cookie);
                        httpServletResponse.addCookie(cookie);
                        count++;
                    }
                }
            }
            httpServletRequest.getSession().invalidate();
            if (count >= 2) {
                responseBean.setRespMsg("退出成功!");
            }
        } catch (Exception e) {
            logger.error("异常信息为", e);
            responseBean.setRespCode(1);
            responseBean.setRespMsg("退出异常!");
        } finally {
            logger.info("后端返回结果为 {}", JSON.toJSONString(responseBean));
            return JSON.toJSONString(responseBean);
        }
    }

}
