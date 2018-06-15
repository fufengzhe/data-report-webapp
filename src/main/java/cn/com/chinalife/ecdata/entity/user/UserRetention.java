package cn.com.chinalife.ecdata.entity.user;

/**
 * Created by xiexiangyu on 2018/3/1.
 */
public class UserRetention {
    private String registerTime;
    private String userSource;
    private String retentionTime;
    private Integer retentionNum;
    private String retentionRate;

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getUserSource() {
        return userSource;
    }

    public void setUserSource(String userSource) {
        this.userSource = userSource;
    }

    public String getRetentionTime() {
        return retentionTime;
    }

    public void setRetentionTime(String retentionTime) {
        this.retentionTime = retentionTime;
    }

    public Integer getRetentionNum() {
        return retentionNum;
    }

    public void setRetentionNum(Integer retentionNum) {
        this.retentionNum = retentionNum;
    }

    public String getRetentionRate() {
        return retentionRate;
    }

    public void setRetentionRate(String retentionRate) {
        this.retentionRate = retentionRate;
    }

    @Override
    public String toString() {
        return "UserRetention{" +
                "registerTime='" + registerTime + '\'' +
                ", userSource='" + userSource + '\'' +
                ", retentionTime='" + retentionTime + '\'' +
                ", retentionNum=" + retentionNum +
                ", retentionRate='" + retentionRate + '\'' +
                '}';
    }
}
