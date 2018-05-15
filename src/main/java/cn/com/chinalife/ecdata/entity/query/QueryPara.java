package cn.com.chinalife.ecdata.entity.query;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
public class QueryPara {
    private String startDate;
    private String endDate;
    private String userSource;
    private String username;
    private String password;
    private String queryDate;
    private String whereCondition;
    private String timeSpan;
    private String distributeType;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    public String getWhereCondition() {
        return whereCondition;
    }

    public void setWhereCondition(String whereCondition) {
        this.whereCondition = whereCondition;
    }

    public String getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    public String getDistributeType() {
        return distributeType;
    }

    public void setDistributeType(String distributeType) {
        this.distributeType = distributeType;
    }

    @Override
    public String toString() {
        return "QueryPara{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", userSource='" + userSource + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", queryDate='" + queryDate + '\'' +
                ", whereCondition='" + whereCondition + '\'' +
                ", timeSpan='" + timeSpan + '\'' +
                ", distributeType='" + distributeType + '\'' +
                '}';
    }
}
