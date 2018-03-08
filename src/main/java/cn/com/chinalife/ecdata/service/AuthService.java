package cn.com.chinalife.ecdata.service;

import cn.com.chinalife.ecdata.entity.user.LogUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by xiexiangyu on 2018/3/8.
 */
public interface AuthService {

    boolean authCheck(LogUser logUser, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException;
}
