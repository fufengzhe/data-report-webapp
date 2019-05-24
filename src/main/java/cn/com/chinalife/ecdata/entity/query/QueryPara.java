package cn.com.chinalife.ecdata.entity.query;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
public class QueryPara {
    private String startDate;
    private String endDate;
    private String userSource;
    private String fromUserSource;
    private String toUserSource;
    private String username;
    private String password;
    private String queryDate;
    private String whereCondition;
    private String whereCondition1;
    private String whereCondition2;
    private String timeSpan;
    private String distributeType;
    private String queryType;

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

    public String getFromUserSource() {
        return fromUserSource;
    }

    public void setFromUserSource(String fromUserSource) {
        this.fromUserSource = fromUserSource;
    }

    public String getToUserSource() {
        return toUserSource;
    }

    public void setToUserSource(String toUserSource) {
        this.toUserSource = toUserSource;
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

    public String getWhereCondition1() {
        return whereCondition1;
    }

    public void setWhereCondition1(String whereCondition1) {
        this.whereCondition1 = whereCondition1;
    }

    public String getWhereCondition2() {
        return whereCondition2;
    }

    public void setWhereCondition2(String whereCondition2) {
        this.whereCondition2 = whereCondition2;
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

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    @Override
    public String toString() {
        return "QueryPara{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", userSource='" + userSource + '\'' +
                ", fromUserSource='" + fromUserSource + '\'' +
                ", toUserSource='" + toUserSource + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", queryDate='" + queryDate + '\'' +
                ", whereCondition='" + whereCondition + '\'' +
                ", whereCondition1='" + whereCondition1 + '\'' +
                ", whereCondition2='" + whereCondition2 + '\'' +
                ", timeSpan='" + timeSpan + '\'' +
                ", distributeType='" + distributeType + '\'' +
                ", queryType='" + queryType + '\'' +
                '}';
    }
}
