package cn.com.chinalife.ecdata.utils;

import cn.com.chinalife.ecdata.entity.user.LogUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
public class AuthUtils {
    private static String resourceOfSalesman = "/salesman/bankAndMobile;/salesman/getBankAndMobileInfo;/salesman/downSampleFile";
    public static List<LogUser> existedAuthLogUserList;

    static {
        init();
    }

    public static void init() {
        String[] usernames = {"admin", "cuiyong", "xuningdi", "zhuhongling", "liufengquan", "guoyuefeng", "xuli", "shiyong", "laijingsi", "caohui", "zhouerwei", "baoyingdong", "wangxinqing", "wangke",
                "zhaodepeng", "gaoxuxiang", "dingyuewu", "zhangguolong", "sunweidong", "yaohua", "wanjing", "guojie", "xusanming", "xiaojuping", "ouyangsijin", "wangli", "pengjinsheng", "wentao",
                "zhangling", "weizhenling", "malingling", "beibei", "longbo", "bazhaoyu", "fanyan", "guojia", "wangxuguang", "lihongxiang", "liuyingjiu", "baohonggang", "xuchaoqun", "songshunli", "gukan",
                "zhanghaifeng", "linyang", "luhuixin", "diaosumeng", "baizhengjun", "jiyubin", "liufei", "huangtao", "zhangqiongzhi", "zhangwenbo", "caiyun", "cuihao", "lilinglong", "xuyanxuan"};
        Map<String, String> usernameAndResource = new HashMap<String, String>();
        usernameAndResource.put("xuyanxuan", resourceOfSalesman);
        AuthUtils.existedAuthLogUserList = new ArrayList<LogUser>();
        for (String username : usernames) {
            LogUser logUser = new LogUser();
            logUser.setUsername(username);
            logUser.setPassword("111111");
            String resource = usernameAndResource.get(username);
            if (resource == null) {
                logUser.setResource("");
            } else {
                logUser.setResource(resource);
            }
            AuthUtils.existedAuthLogUserList.add(logUser);
        }
//        LogUser active = new LogUser();
//        active.setUsername("active");
//        active.setPassword("111111");
//        active.setResource("/activeUser/numQuery;/activeUser/detailQuery");
//        AuthUtils.existedAuthLogUserList.add(active);
    }

}
