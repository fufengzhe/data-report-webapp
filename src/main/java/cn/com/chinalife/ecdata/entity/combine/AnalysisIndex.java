package cn.com.chinalife.ecdata.entity.combine;

/**
 * Created by xiexiangyu on 2018/4/20.
 */
public class AnalysisIndex {
    private int registerNum;
    private int activeNum;
    private String statDate;
    private String statHour;
    private String statDateSpan;
    private String indexName;
    private String indexSource;
    private String fromUserSource;
    private String toUserSource;
    private String distributeType;
    private String distributeName;
    private Integer indexValue;
    private Integer fromIndexValue;
    private Integer toIndexValue;
    private String mobile;
    private String ip;


    public int getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(int registerNum) {
        this.registerNum = registerNum;
    }

    public int getActiveNum() {
        return activeNum;
    }

    public void setActiveNum(int activeNum) {
        this.activeNum = activeNum;
    }

    public String getStatDate() {
        return statDate;
    }

    public void setStatDate(String statDate) {
        this.statDate = statDate;
    }

    public String getStatHour() {
        return statHour;
    }

    public void setStatHour(String statHour) {
        this.statHour = statHour;
    }

    public String getStatDateSpan() {
        return statDateSpan;
    }

    public void setStatDateSpan(String statDateSpan) {
        this.statDateSpan = statDateSpan;
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

    public String getDistributeType() {
        return distributeType;
    }

    public void setDistributeType(String distributeType) {
        this.distributeType = distributeType;
    }

    public String getDistributeName() {
        return distributeName;
    }

    public void setDistributeName(String distributeName) {
        this.distributeName = distributeName;
    }

    public Integer getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(Integer indexValue) {
        this.indexValue = indexValue;
    }

    public Integer getFromIndexValue() {
        return fromIndexValue;
    }

    public void setFromIndexValue(Integer fromIndexValue) {
        this.fromIndexValue = fromIndexValue;
    }

    public Integer getToIndexValue() {
        return toIndexValue;
    }

    public void setToIndexValue(Integer toIndexValue) {
        this.toIndexValue = toIndexValue;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "AnalysisIndex{" +
                "registerNum=" + registerNum +
                ", activeNum=" + activeNum +
                ", statDate='" + statDate + '\'' +
                ", statHour='" + statHour + '\'' +
                ", statDateSpan='" + statDateSpan + '\'' +
                ", indexName='" + indexName + '\'' +
                ", indexSource='" + indexSource + '\'' +
                ", fromUserSource='" + fromUserSource + '\'' +
                ", toUserSource='" + toUserSource + '\'' +
                ", distributeType='" + distributeType + '\'' +
                ", distributeName='" + distributeName + '\'' +
                ", indexValue=" + indexValue +
                ", fromIndexValue=" + fromIndexValue +
                ", toIndexValue=" + toIndexValue +
                ", mobile='" + mobile + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
