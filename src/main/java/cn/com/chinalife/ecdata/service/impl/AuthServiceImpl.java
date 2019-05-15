package cn.com.chinalife.ecdata.service.impl;

import cn.com.chinalife.ecdata.dao.sqlDao.AuthDao;
import cn.com.chinalife.ecdata.dao.sqlDao.user.LogUserDao;
import cn.com.chinalife.ecdata.entity.ResponseBean;
import cn.com.chinalife.ecdata.entity.user.LogUser;
import cn.com.chinalife.ecdata.service.AuthService;
import cn.com.chinalife.ecdata.utils.CommonConstant;
import cn.com.chinalife.ecdata.utils.DataSourceContextHolder;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by xiexiangyu on 2018/3/8.
 */

@Service
public class AuthServiceImpl implements AuthService {
    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    @Autowired
    AuthDao authDao;
    @Autowired
    LogUserDao logUserDao;

    public boolean authCheck(LogUser logUser, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        logger.info("interceptor传入的参数为 {}", JSON.toJSONString(logUser));
//        DataSourceContextHolder.setDbType(CommonConstant.userAuthDataSource);
//        List<Auth> authList = authDao.getAuthUsingLogUser(logUser);
//        if (authList.size() > 0) {
//            if ("0".equals(authList.get(0).getResourceId())) {
//                authList = authDao.getAllAuth();
//            } else {
//                authList = authDao.getAuthUsingResourceId(authList);
//            }
//            String path = httpServletRequest.getServletPath();
//            for (Auth auth : authList) {
//                if (path != null && path.contains(auth.getResourcePath())) {
//                    logger.info("service返回结果为 {},请求路径为 {}", JSON.toJSONString(authList), path);
//                    return true;
//                }
//            }
//        }
        String resource = this.getResourceUsingUsername(logUser);
        if (resource != null) {
            // ""表示所有权限
            if ("ALL".equals(resource)) {
                logger.info("当前用户为最高权限!");
                return true;
            } else {
                String currentPath = httpServletRequest.getServletPath();
                logger.info("当前用户所有授权资源为 {},当前请求路径为 {}", JSON.toJSONString(resource), currentPath);
                if (resource.contains(currentPath)) {
                    return true;
                } else {
                    logger.info("interceptor未检查到授权信息");
                    ResponseBean responseBean = new ResponseBean();
                    responseBean.setRespCode(1);
                    responseBean.setRespMsg(CommonConstant.unauthorized);
                    httpServletResponse.getWriter().print(JSON.toJSONString(responseBean));
                    httpServletResponse.sendRedirect("/ecdata/init/unauthorized");
                    return false;
                }
            }
        }
        ResponseBean responseBean = new ResponseBean();
        responseBean.setRespCode(1);
        responseBean.setRespMsg(CommonConstant.unauthorized);
        httpServletResponse.getWriter().print(JSON.toJSONString(responseBean));
        logger.info("interceptor未检查到授权信息");
        return false;
    }


    private String getResourceUsingUsername(LogUser logUser) {
        DataSourceContextHolder.setDbType(CommonConstant.businessDataSource);
        LogUser temp = logUserDao.findUserResourcesUsingName(logUser);
//        LogUser temp = logUserDao.findUserResourcesFromMysqlUsingName(logUser);
        if (temp != null) {
            return temp.getResources();
        } else {
            return null;
        }
    }
}
