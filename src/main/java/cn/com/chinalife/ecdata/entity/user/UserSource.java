package cn.com.chinalife.ecdata.entity.user;

/**
 * Created by xiexiangyu on 2018/3/6.
 */
public class UserSource {
    private String userSource;
    private String userSourceName;

    public String getUserSource() {
        return userSource;
    }

    public void setUserSource(String userSource) {
        this.userSource = userSource;
    }

    public String getUserSourceName() {
        return userSourceName;
    }

    public void setUserSourceName(String userSourceName) {
        this.userSourceName = userSourceName;
    }

    @Override
    public String toString() {
        return "UserSource{" +
                "userSource='" + userSource + '\'' +
                ", userSourceName='" + userSourceName + '\'' +
                '}';
    }
}
