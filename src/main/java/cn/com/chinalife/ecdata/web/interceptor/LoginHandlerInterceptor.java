package cn.com.chinalife.ecdata.web.interceptor;

import cn.com.chinalife.ecdata.entity.ResponseBean;
import cn.com.chinalife.ecdata.entity.user.LogUser;
import cn.com.chinalife.ecdata.service.AuthService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xiexiangyu on 2018/3/7.
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(LoginHandlerInterceptor.class);

    @Autowired
    AuthService authService;

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        LogUser logUser = (LogUser) httpServletRequest.getSession().getAttribute("user");
        if (logUser == null) {
            Cookie[] cookies = httpServletRequest.getCookies();
            if (cookies != null) {
                String username = null;
                for (Cookie cookie : cookies) {
                    if ("username".equals(cookie.getName())) {
                        username = cookie.getValue();
                    }
                }
                if (username != null) {
                    logger.info("interceptor检查到cookie信息 username:{}", username);
                    logUser = new LogUser();
                    logUser.setUsername(username);
                    return authService.authCheck(logUser, httpServletRequest, httpServletResponse);
                }
            }
            ResponseBean responseBean = new ResponseBean();
            responseBean.setRespCode(1);
            responseBean.setRespMsg(CommonConstant.notLoggedIn);
            httpServletResponse.getWriter().print(JSON.toJSONString(responseBean));
            logger.info("interceptor未检查到cookie信息");
            return false;
        }
        logger.info("interceptor检查到session信息 user:{}", JSON.toJSONString(logUser));
        return authService.authCheck(logUser, httpServletRequest, httpServletResponse);
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
