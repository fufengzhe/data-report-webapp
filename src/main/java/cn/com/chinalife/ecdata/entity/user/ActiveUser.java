package cn.com.chinalife.ecdata.entity.user;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
public class ActiveUser {
    private int activeUserNum;
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
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", userSource='" + userSource + '\'' +
                ", timeSpan='" + timeSpan + '\'' +
                ", indexName='" + indexName + '\'' +
                ", statDate='" + statDate + '\'' +
                '}';
    }
}
