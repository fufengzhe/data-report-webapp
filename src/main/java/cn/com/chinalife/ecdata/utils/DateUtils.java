package cn.com.chinalife.ecdata.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xiexiangyu on 2018/3/30.
 */
public class DateUtils {

    public static String getYesterday() {
        Calendar current = Calendar.getInstance();
        current.add(Calendar.DATE, -1);
        return new SimpleDateFormat("yyyy-MM-dd").format(current.getTime());
    }

    public static String getBeforeXDay(int num) {
        Calendar current = Calendar.getInstance();
        current.add(Calendar.DATE, -num);
        return new SimpleDateFormat("yyyy-MM-dd").format(current.getTime());
    }

    public static String getTheDayBeforeYesterday() {
        Calendar current = Calendar.getInstance();
        current.add(Calendar.DATE, -2);
        return new SimpleDateFormat("yyyy-MM-dd").format(current.getTime());
    }

    public static String getMonthBeginDateUsingYesterday(String yesterday) {
        String month = yesterday.substring(0, 7);
        return month + "-01";
    }

    public static String getLastMonthBeginDateUsingYesterday(String yesterday) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(yesterday);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        return format.format(calendar.getTime()).substring(0, 7) + "-01";
    }

    public static String getYearBeginDateUsingYesterday(String yesterday) {
        String month = yesterday.substring(0, 4);
        return month + "-01-01";
    }

    public static String addXDateBasedGivenDate(String givenDate, int span) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(format.parse(givenDate));
        calendar.add(Calendar.DATE, span);
        return format.format(calendar.getTime());
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(getBeforeXDay(1));
        System.out.println(getBeforeXDay(7));
        System.out.println(addXDateBasedGivenDate("2018-04-30", 2));
    }

}
