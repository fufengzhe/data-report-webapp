package cn.com.chinalife.ecdata.entity.userShare;

/**
 * Created by xiexiangyu on 2018/6/7.
 */
public class UserShare {
    private String statDate;
    private String statTimeSpan;
    private String indexName;
    private String distributeType;
    private String statHour;
    private String userSource;
    private Integer shareNum;
    private Integer shareNum7;
    private Integer shareNum6;
    private Integer shareNum5;
    private Integer shareNum4;
    private Integer shareNum3;
    private Integer shareNum2;
    private Integer shareNum1;

    public String getStatDate() {
        return statDate;
    }

    public void setStatDate(String statDate) {
        this.statDate = statDate;
    }

    public String getStatTimeSpan() {
        return statTimeSpan;
    }

    public void setStatTimeSpan(String statTimeSpan) {
        this.statTimeSpan = statTimeSpan;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getDistributeType() {
        return distributeType;
    }

    public void setDistributeType(String distributeType) {
        this.distributeType = distributeType;
    }

    public String getStatHour() {
        return statHour;
    }

    public void setStatHour(String statHour) {
        this.statHour = statHour;
    }

    public String getUserSource() {
        return userSource;
    }

    public void setUserSource(String userSource) {
        this.userSource = userSource;
    }

    public Integer getShareNum() {
        return shareNum;
    }

    public void setShareNum(Integer shareNum) {
        this.shareNum = shareNum;
    }

    public Integer getShareNum7() {
        return shareNum7;
    }

    public void setShareNum7(Integer shareNum7) {
        this.shareNum7 = shareNum7;
    }

    public Integer getShareNum6() {
        return shareNum6;
    }

    public void setShareNum6(Integer shareNum6) {
        this.shareNum6 = shareNum6;
    }

    public Integer getShareNum5() {
        return shareNum5;
    }

    public void setShareNum5(Integer shareNum5) {
        this.shareNum5 = shareNum5;
    }

    public Integer getShareNum4() {
        return shareNum4;
    }

    public void setShareNum4(Integer shareNum4) {
        this.shareNum4 = shareNum4;
    }

    public Integer getShareNum3() {
        return shareNum3;
    }

    public void setShareNum3(Integer shareNum3) {
        this.shareNum3 = shareNum3;
    }

    public Integer getShareNum2() {
        return shareNum2;
    }

    public void setShareNum2(Integer shareNum2) {
        this.shareNum2 = shareNum2;
    }

    public Integer getShareNum1() {
        return shareNum1;
    }

    public void setShareNum1(Integer shareNum1) {
        this.shareNum1 = shareNum1;
    }

    @Override
    public String toString() {
        return "UserShare{" +
                "statDate='" + statDate + '\'' +
                ", statTimeSpan='" + statTimeSpan + '\'' +
                ", indexName='" + indexName + '\'' +
                ", distributeType='" + distributeType + '\'' +
                ", statHour='" + statHour + '\'' +
                ", userSource='" + userSource + '\'' +
                ", shareNum=" + shareNum +
                ", shareNum7=" + shareNum7 +
                ", shareNum6=" + shareNum6 +
                ", shareNum5=" + shareNum5 +
                ", shareNum4=" + shareNum4 +
                ", shareNum3=" + shareNum3 +
                ", shareNum2=" + shareNum2 +
                ", shareNum1=" + shareNum1 +
                '}';
    }
}
