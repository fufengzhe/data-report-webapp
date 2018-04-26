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
        String[] usernames = {"admin", "cuiyong", "xuningdi", "zhuhongling", "liufengquan", "guoyuefeng", "xuli", "shiyong", "laijingsi", "caohui", "zhouerwei", "baoyingdong", "wangxinqing", "wangke",
                "zhaodepeng", "gaoxuxiang", "dingyuewu", "zhangguolong", "sunweidong", "yaohua", "wanjing", "guojie", "xusanming", "xiaojuping", "ouyangsijin", "wangli", "pengjinsheng", "wentao",
                "zhangling", "weizhenling", "malingling", "beibei", "longbo", "bazhaoyu", "fanyan", "guojia", "wangxuguang", "lihongxiang", "liuyingjiu", "baohonggang", "xuchaoqun", "songshunli", "gukan",
                "zhanghaifeng", "linyang", "luhuixin", "diaosumeng", "baizhengjun", "jiyubin", "liufei", "huangtao", "zhangqiongzhi", "zhangwenbo", "caiyun", "cuihao", "lilinglong"};
        AuthUtils.existedAuthLogUserList = new ArrayList<LogUser>();
        for (String username : usernames) {
            LogUser logUser = new LogUser();
            logUser.setUsername(username);
            logUser.setPassword("111111");
            logUser.setResource("");
            AuthUtils.existedAuthLogUserList.add(logUser);
        }
//        LogUser active = new LogUser();
//        active.setUsername("active");
//        active.setPassword("111111");
//        active.setResource("/activeUser/numQuery;/activeUser/detailQuery");
//        AuthUtils.existedAuthLogUserList.add(active);
    }

}
