package cn.com.chinalife.ecdata.entity.user;

/**
 * Created by xiexiangyu on 2018/3/2.
 */
public class RegisterUser {
    private int registerUserNum;
    private String startDate;
    private String endDate;
    private String userSource;

    public int getRegisterUserNum() {
        return registerUserNum;
    }

    public void setRegisterUserNum(int registerUserNum) {
        this.registerUserNum = registerUserNum;
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
        return "RegisterUser{" +
                "registerUserNum=" + registerUserNum +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", userSource='" + userSource + '\'' +
                '}';
    }
}
