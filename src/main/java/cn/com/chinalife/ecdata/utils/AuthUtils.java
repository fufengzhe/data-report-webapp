package cn.com.chinalife.ecdata.utils;

import cn.com.chinalife.ecdata.dao.sqlDao.user.LogUserDao;
import cn.com.chinalife.ecdata.entity.user.LogUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
public class AuthUtils {
    public static List<LogUser> existedAuthLogUserList;
    @Autowired
    private static LogUserDao logUserDao;

    static {
        init();
    }

    public static void init() {
        AuthUtils.existedAuthLogUserList = logUserDao.getAllValidUserInfo();
    }

}
