package cn.com.chinalife.ecdata.utils;

import javax.servlet.http.Cookie;

/**
 * Created by xiexiangyu on 2018/3/8.
 */
public class CommonUtils {
    public static void setCookieInvalid(Cookie cookieToSet) {
        cookieToSet.setValue(null);
        cookieToSet.setMaxAge(0);
        cookieToSet.setPath("/");
    }
}
