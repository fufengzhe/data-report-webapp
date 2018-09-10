package cn.com.chinalife.ecdata.utils;

import cn.com.chinalife.ecdata.entity.trade.Premium;
import cn.com.chinalife.ecdata.entity.user.UserSource;

import javax.servlet.http.Cookie;
import java.math.BigDecimal;
import java.security.MessageDigest;
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

    public static String getPercentageStr(BigDecimal bigDecimal, int roundMode) {
        if (bigDecimal == null) {
            return null;
        } else {
            return bigDecimal.multiply(new BigDecimal("100")).setScale(2, roundMode) + "%";
        }
    }

    //单位从个转换为万
    public static BigDecimal convertToTenThousandUnit(BigDecimal numToConvert) {
        if (numToConvert == BigDecimal.ZERO || numToConvert.doubleValue() == 0) {
            return BigDecimal.ZERO;
        } else {
            return divideWithXPrecision(numToConvert, new BigDecimal(10000), 2);
        }
    }

    public static void convertPremium(List<Premium> premiumList) {
        for (Premium premium : premiumList) {
            premium.setDayAmount(CommonUtils.convertToTenThousandUnit(premium.getDayAmount()));
            premium.setLastDayAmount(CommonUtils.convertToTenThousandUnit(premium.getLastDayAmount()));
            premium.setMonthAmount(CommonUtils.convertToTenThousandUnit(premium.getMonthAmount()));
            premium.setLastMonthAmount(CommonUtils.convertToTenThousandUnit(premium.getLastMonthAmount()));
            premium.setYearAmount(CommonUtils.convertToTenThousandUnit(premium.getYearAmount()));
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

    public static void setDefaultForPremium(Premium premium) {
        premium.setDayAmount(new BigDecimal("0.00"));
        premium.setLastDayAmount(new BigDecimal("0.00"));
        premium.setMonthAmount(new BigDecimal("0.00"));
        premium.setLastMonthAmount(new BigDecimal("0.00"));
        premium.setYearAmount(new BigDecimal("0.00"));
    }

    public static String getMD5(String str) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update((str).getBytes("UTF-8"));
        byte b[] = md5.digest();
        int i;
        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                buf.append("0");
            }
            buf.append(Integer.toHexString(i));
        }
        return buf.toString();
    }

    public static void convertToTenThousandUnitForPremium(List<Premium> premiumList) {
        for (Premium premium : premiumList) {
            premium.setAccumulatedAmount7(CommonUtils.convertToTenThousandUnit(premium.getAccumulatedAmount7()));
            premium.setAccumulatedAmount6(CommonUtils.convertToTenThousandUnit(premium.getAccumulatedAmount6()));
            premium.setAccumulatedAmount5(CommonUtils.convertToTenThousandUnit(premium.getAccumulatedAmount5()));
            premium.setAccumulatedAmount4(CommonUtils.convertToTenThousandUnit(premium.getAccumulatedAmount4()));
            premium.setAccumulatedAmount3(CommonUtils.convertToTenThousandUnit(premium.getAccumulatedAmount3()));
            premium.setAccumulatedAmount2(CommonUtils.convertToTenThousandUnit(premium.getAccumulatedAmount2()));
            premium.setAccumulatedAmount1(CommonUtils.convertToTenThousandUnit(premium.getAccumulatedAmount1()));
        }
    }

    public static String getStrJoinWithComma(List<UserSource> userSourceList) {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (int i = 0; i < userSourceList.size() - 1; i++) {
            stringBuilder.append("\"").append(userSourceList.get(i).getUserSource()).append("\",");
        }
        stringBuilder.append("\"").append(userSourceList.get(userSourceList.size() - 1).getUserSource()).append("\"");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static String getWhereConditionUsingPara(String userSource) {
        if (userSource != null) {
            String[] temp = userSource.split(",");
            StringBuilder userSourceCondition = new StringBuilder("(");
            for (int i = 0; i < temp.length - 1; i++) {
                userSourceCondition.append("'").append(temp[i]).append("',");
            }
            userSourceCondition.append("'").append(temp[temp.length - 1]).append("')");
            return userSourceCondition.toString();
        } else {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            String s=null;
            System.out.println(s.length());
        }catch (Exception e){

        }
        System.out.println("finish");
    }
}
