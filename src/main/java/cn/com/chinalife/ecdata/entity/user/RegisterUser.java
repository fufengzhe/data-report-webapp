package cn.com.chinalife.ecdata.entity.user;

import java.math.BigDecimal;

/**
 * Created by xiexiangyu on 2018/3/2.
 */
public class RegisterUser {
    private int registerUserNum;
    private String startDate;
    private String endDate;
    private String userSource;
    private String statDay;
    private String secondName;
    private BigDecimal dayNum;
    private BigDecimal lastDayNum;
    private String dayRatio;
    private BigDecimal monthNum;
    private BigDecimal lastMonthNum;
    private String monthRatio;
    private BigDecimal yearNum;
    private String yearNumStr; //百分比
    private BigDecimal yearGoal;
    private String completeRatio;
    private String registerDate;
    private String timeSpan;
    private String indexName;
    private String indexSource;

    public int getRegisterUserNum() {
        return registerUserNum;
    }

    public void setRegisterUserNum(int registerUserNum) {
        this.registerUserNum = registerUserNum;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUserSource() {
        return userSource;
    }

    public void setUserSource(String userSource) {
        this.userSource = userSource;
    }

    public String getStatDay() {
        return statDay;
    }

    public void setStatDay(String statDay) {
        this.statDay = statDay;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public BigDecimal getDayNum() {
        return dayNum;
    }

    public void setDayNum(BigDecimal dayNum) {
        this.dayNum = dayNum;
    }

    public BigDecimal getLastDayNum() {
        return lastDayNum;
    }

    public void setLastDayNum(BigDecimal lastDayNum) {
        this.lastDayNum = lastDayNum;
    }

    public String getDayRatio() {
        return dayRatio;
    }

    public void setDayRatio(String dayRatio) {
        this.dayRatio = dayRatio;
    }

    public BigDecimal getMonthNum() {
        return monthNum;
    }

    public void setMonthNum(BigDecimal monthNum) {
        this.monthNum = monthNum;
    }

    public BigDecimal getLastMonthNum() {
        return lastMonthNum;
    }

    public void setLastMonthNum(BigDecimal lastMonthNum) {
        this.lastMonthNum = lastMonthNum;
    }

    public String getMonthRatio() {
        return monthRatio;
    }

    public void setMonthRatio(String monthRatio) {
        this.monthRatio = monthRatio;
    }

    public BigDecimal getYearNum() {
        return yearNum;
    }

    public void setYearNum(BigDecimal yearNum) {
        this.yearNum = yearNum;
    }

    public String getYearNumStr() {
        return yearNumStr;
    }

    public void setYearNumStr(String yearNumStr) {
        this.yearNumStr = yearNumStr;
    }

    public BigDecimal getYearGoal() {
        return yearGoal;
    }

    public void setYearGoal(BigDecimal yearGoal) {
        this.yearGoal = yearGoal;
    }

    public String getCompleteRatio() {
        return completeRatio;
    }

    public void setCompleteRatio(String completeRatio) {
        this.completeRatio = completeRatio;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getIndexSource() {
        return indexSource;
    }

    public void setIndexSource(String indexSource) {
        this.indexSource = indexSource;
    }

    @Override
    public String toString() {
        return "RegisterUser{" +
                "registerUserNum=" + registerUserNum +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", userSource='" + userSource + '\'' +
                ", statDay='" + statDay + '\'' +
                ", secondName='" + secondName + '\'' +
                ", dayNum=" + dayNum +
                ", lastDayNum=" + lastDayNum +
                ", dayRatio='" + dayRatio + '\'' +
                ", monthNum=" + monthNum +
                ", lastMonthNum=" + lastMonthNum +
                ", monthRatio='" + monthRatio + '\'' +
                ", yearNum=" + yearNum +
                ", yearNumStr='" + yearNumStr + '\'' +
                ", yearGoal=" + yearGoal +
                ", completeRatio='" + completeRatio + '\'' +
                ", registerDate='" + registerDate + '\'' +
                ", timeSpan='" + timeSpan + '\'' +
                ", indexName='" + indexName + '\'' +
                ", indexSource='" + indexSource + '\'' +
                '}';
    }
}
