package cn.com.chinalife.ecdata.entity.user;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
public class ActiveUser {
    private int activeUserNum;
    private String startDate;
    private String endDate;
    private String userSource;

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

    @Override
    public String toString() {
        return "ActiveUser{" +
                "activeUserNum=" + activeUserNum +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", userSource='" + userSource + '\'' +
                '}';
    }
}
