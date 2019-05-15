package cn.com.chinalife.ecdata.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
public class CommonConstant {
    public static String beginAppendTime = " 00:00:00";
    public static String endAppendTime = " 23:59:59";
    public static String queryFailureStr = "查询失败!";
    public static String updateFailureStr = "更新失败!";
    public static String logFailureStr = "登录失败!";
    public static String downFailureStr = "下载失败!";
    public static String userAuthDataSource = "userAuthDataSource";
    public static String businessDataSource = "businessDataSource";
    public static String fupinDataSource = "fupinDataSource";
    public static String saleOrderDataSource = "saleOrderDataSource";
    public static String saleUserDataSource = "saleUserDataSource";
    public static String notLoggedIn = "notLoggedIn";
    public static String unauthorized = "unauthorized";
    public static String statTimeSpanOfDate = "D";
    public static String statTimeSpanOfMonth = "M";
    public static String statTimeSpanOfYear = "Y";
    public static String statIndexNameOfRegister = "registerNum";
    public static String statIndexNameOfActive = "activeNum";
    public static String distributeIndexNameOfRegisterMobile = "registerMobile";
    public static String distributeIndexNameOfActiveIP = "activeIP";
    public static String distributeIndexNameOfActiveHour = "activeHour";
    public static String distributeIndexNameOfUserCollection = "userCollection";
    public static String distributeIndexNameOfMigrateCollection = "migrateCollection";
    public static String distributeIndexNameOfMigrateCollectionUserNum = "migrateCollectionUserNum";
    public static String statIndexNameOfPropertyPremiumPTPG = "propertyPremiumPTPG";
    public static String statIndexNameOfPropertyPremium = "propertyPremium";
    public static String statIndexNameOfLifePremium = "lifePremium";
    public static String statIndexNameOfUserShare = "userShare";
    public static String statIndexNameOfUserAge = "userAge";
    public static String statIndexNameOfUserRank = "userRank";
    public static String statIndexNameOfUserSex = "userSex";
    public static String statIndexNameOfFupinPageClick = "fupinPageClick";
    public static String statIndexNameOfFupinPageClickIPInfo = "fupinPageClickIPInfo";
    public static String statIndexNameOfFupinOrderStat = "fupinOrderStat";
    public static String statIndexNameOfFupinOrderIPInfo = "fupinOrderIPInfo";
    public static String statIndexNameOfFupinOrderFromToAreaInfo = "fupinOrderFromToAreaInfo";
    public static String userSourceOfAll = "ALL";
    public static int jsVersion = 13;
    public static List<String> statIndexNameListOfPropertyPremium;

    static {
        statIndexNameListOfPropertyPremium = new ArrayList<String>();
        statIndexNameListOfPropertyPremium.add(statIndexNameOfPropertyPremiumPTPG);
        statIndexNameListOfPropertyPremium.add(statIndexNameOfPropertyPremium);
    }

}
