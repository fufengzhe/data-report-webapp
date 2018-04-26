package cn.com.chinalife.ecdata.entity.user;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
public class ActiveUser {
    private int activeUserNum;
    private int activeUserNumOf7;
    private int activeUserNumOf6;
    private int activeUserNumOf5;
    private int activeUserNumOf4;
    private int activeUserNumOf3;
    private int activeUserNumOf2;
    private int activeUserNumOf1;
    private String startDate;
    private String endDate;
    private String userSource;
    private String timeSpan;
    private String indexName;
    private String statDate;

    public int getActiveUserNum() {
        return activeUserNum;
    }

    public void setActiveUserNum(int activeUserNum) {
        this.activeUserNum = activeUserNum;
    }

    public int getActiveUserNumOf7() {
        return activeUserNumOf7;
    }

    public void setActiveUserNumOf7(int activeUserNumOf7) {
        this.activeUserNumOf7 = activeUserNumOf7;
    }

    public int getActiveUserNumOf6() {
        return activeUserNumOf6;
    }

    public void setActiveUserNumOf6(int activeUserNumOf6) {
        this.activeUserNumOf6 = activeUserNumOf6;
    }

    public int getActiveUserNumOf5() {
        return activeUserNumOf5;
    }

    public void setActiveUserNumOf5(int activeUserNumOf5) {
        this.activeUserNumOf5 = activeUserNumOf5;
    }

    public int getActiveUserNumOf4() {
        return activeUserNumOf4;
    }

    public void setActiveUserNumOf4(int activeUserNumOf4) {
        this.activeUserNumOf4 = activeUserNumOf4;
    }

    public int getActiveUserNumOf3() {
        return activeUserNumOf3;
    }

    public void setActiveUserNumOf3(int activeUserNumOf3) {
        this.activeUserNumOf3 = activeUserNumOf3;
    }

    public int getActiveUserNumOf2() {
        return activeUserNumOf2;
    }

    public void setActiveUserNumOf2(int activeUserNumOf2) {
        this.activeUserNumOf2 = activeUserNumOf2;
    }

    public int getActiveUserNumOf1() {
        return activeUserNumOf1;
    }

    public void setActiveUserNumOf1(int activeUserNumOf1) {
        this.activeUserNumOf1 = activeUserNumOf1;
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

    public String getStatDate() {
        return statDate;
    }

    public void setStatDate(String statDate) {
        this.statDate = statDate;
    }

    @Override
    public String toString() {
        return "ActiveUser{" +
                "activeUserNum=" + activeUserNum +
                ", activeUserNumOf7=" + activeUserNumOf7 +
                ", activeUserNumOf6=" + activeUserNumOf6 +
                ", activeUserNumOf5=" + activeUserNumOf5 +
                ", activeUserNumOf4=" + activeUserNumOf4 +
                ", activeUserNumOf3=" + activeUserNumOf3 +
                ", activeUserNumOf2=" + activeUserNumOf2 +
                ", activeUserNumOf1=" + activeUserNumOf1 +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", userSource='" + userSource + '\'' +
                ", timeSpan='" + timeSpan + '\'' +
                ", indexName='" + indexName + '\'' +
                ", statDate='" + statDate + '\'' +
                '}';
    }
}
