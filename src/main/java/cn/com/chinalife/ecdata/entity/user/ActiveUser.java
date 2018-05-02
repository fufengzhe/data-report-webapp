package cn.com.chinalife.ecdata.entity.user;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
public class ActiveUser {
    private Integer activeUserNum;
    private Integer activeUserNumOf7;
    private Integer activeUserNumOf6;
    private Integer activeUserNumOf5;
    private Integer activeUserNumOf4;
    private Integer activeUserNumOf3;
    private Integer activeUserNumOf2;
    private Integer activeUserNumOf1;
    private String startDate;
    private String endDate;
    private String userSource;
    private String timeSpan;
    private String indexName;
    private String statDate;

    public Integer getActiveUserNum() {
        return activeUserNum;
    }

    public void setActiveUserNum(Integer activeUserNum) {
        this.activeUserNum = activeUserNum;
    }

    public Integer getActiveUserNumOf7() {
        return activeUserNumOf7;
    }

    public void setActiveUserNumOf7(Integer activeUserNumOf7) {
        this.activeUserNumOf7 = activeUserNumOf7;
    }

    public Integer getActiveUserNumOf6() {
        return activeUserNumOf6;
    }

    public void setActiveUserNumOf6(Integer activeUserNumOf6) {
        this.activeUserNumOf6 = activeUserNumOf6;
    }

    public Integer getActiveUserNumOf5() {
        return activeUserNumOf5;
    }

    public void setActiveUserNumOf5(Integer activeUserNumOf5) {
        this.activeUserNumOf5 = activeUserNumOf5;
    }

    public Integer getActiveUserNumOf4() {
        return activeUserNumOf4;
    }

    public void setActiveUserNumOf4(Integer activeUserNumOf4) {
        this.activeUserNumOf4 = activeUserNumOf4;
    }

    public Integer getActiveUserNumOf3() {
        return activeUserNumOf3;
    }

    public void setActiveUserNumOf3(Integer activeUserNumOf3) {
        this.activeUserNumOf3 = activeUserNumOf3;
    }

    public Integer getActiveUserNumOf2() {
        return activeUserNumOf2;
    }

    public void setActiveUserNumOf2(Integer activeUserNumOf2) {
        this.activeUserNumOf2 = activeUserNumOf2;
    }

    public Integer getActiveUserNumOf1() {
        return activeUserNumOf1;
    }

    public void setActiveUserNumOf1(Integer activeUserNumOf1) {
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
