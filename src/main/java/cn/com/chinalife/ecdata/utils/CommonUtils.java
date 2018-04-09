package cn.com.chinalife.ecdata.utils;

import cn.com.chinalife.ecdata.entity.trade.Premium;

import javax.servlet.http.Cookie;
import java.math.BigDecimal;
import java.util.*;

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

    //单位从个转换为万
    public static BigDecimal convertToTenThousandUnit(BigDecimal numToConvert) {
        if (numToConvert == BigDecimal.ZERO || numToConvert.doubleValue() == 0) {
            return BigDecimal.ZERO;
        }else{
            return divideWithXPrecision(numToConvert,new BigDecimal(10000),2);
        }
    }

    public static List<Premium> getPremiumListUsingMap(Map<String, BigDecimal> map) {
        List<Premium> premiumList = new ArrayList<Premium>();
        for (Map.Entry<String, BigDecimal> entry : map.entrySet()) {
            Premium premium = new Premium();
            premium.setBranchName(entry.getKey());
            premium.setAccumulatedAmount(CommonUtils.divideWithXPrecision(entry.getValue(), new BigDecimal("10000"), 2)); //换算成万
            premiumList.add(premium);
        }
        Collections.sort(premiumList, new Comparator<Premium>() {
            public int compare(Premium o1, Premium o2) {
                return o2.getAccumulatedAmount().subtract(o1.getAccumulatedAmount()).compareTo(new BigDecimal("0"));
            }
        });
        return premiumList;
    }


    public static void main(String[] args) {
        System.out.println(convertToTenThousandUnit(new BigDecimal(11565.11)));
    }
}
