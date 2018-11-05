package cn.com.chinalife.ecdata.entity;

/**
 * Created by xiexiangyu on 2018/9/7.
 */
public class IPInfo {
    private String ip;
    private String province;
    private String city;
    private String company;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "IPInfo{" +
                "ip='" + ip + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
