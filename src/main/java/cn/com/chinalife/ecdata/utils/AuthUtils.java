package cn.com.chinalife.ecdata.utils;

import cn.com.chinalife.ecdata.entity.user.LogUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
public class AuthUtils {
    String resourceOfActive = "/activeUser/numQuery";
    public static List<LogUser> existedAuthLogUserList;

    static {
        init();
    }

    public static void init() {
        AuthUtils.existedAuthLogUserList = new ArrayList<LogUser>();
        LogUser admin = new LogUser();
        admin.setUsername("admin");
        admin.setPassword("111111");
        admin.setResource(""); //""标识拥有所有权限
        AuthUtils.existedAuthLogUserList.add(admin);
        LogUser active = new LogUser();
        active.setUsername("active");
        active.setPassword("111111");
        active.setResource("/activeUser/numQuery");
        AuthUtils.existedAuthLogUserList.add(active);
    }

}
