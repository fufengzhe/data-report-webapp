package cn.com.chinalife.ecdata.utils;

import javax.servlet.http.Cookie;
import java.math.BigDecimal;

/**
 * Created by xiexiangyu on 2018/3/8.
 */
public class CommonUtils {
    public static void setCookieInvalid(Cookie cookieToSet) {
        cookieToSet.setValue(null);
        cookieToSet.setMaxAge(0);
        cookieToSet.setPath("/");
    }

    public static BigDecimal divideWithXPrecision(BigDecimal val1, BigDecimal val2, int scale) {
        BigDecimal result = null;
        if (val2 == BigDecimal.ZERO || val2.doubleValue() == 0) {
            result = BigDecimal.ZERO;
        } else {
            result = val1.divide(val2, scale, BigDecimal.ROUND_HALF_UP);
        }
        return result;
    }

    public static String getPercentageStr(BigDecimal bigDecimal) {
        return bigDecimal.multiply(new BigDecimal("100")).setScale(2) + "%";
    }

    public static void main(String[] args) {
        System.out.println(divideWithXPrecision(new BigDecimal("11"), new BigDecimal("211"), 4));
        System.out.println(getPercentageStr(divideWithXPrecision(new BigDecimal("11"), new BigDecimal("211"), 4)));
    }
}
