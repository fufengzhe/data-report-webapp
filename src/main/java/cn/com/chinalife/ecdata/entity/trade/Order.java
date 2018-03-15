package cn.com.chinalife.ecdata.entity.trade;

import java.math.BigDecimal;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
public class Order {
    private String policyNoJQ;
    private String policyNoSY;
    private String policyNo;
    private String departNo;
    private BigDecimal premium;

    public String getPolicyNoJQ() {
        return policyNoJQ;
    }

    public void setPolicyNoJQ(String policyNoJQ) {
        this.policyNoJQ = policyNoJQ;
    }

    public String getPolicyNoSY() {
        return policyNoSY;
    }

    public void setPolicyNoSY(String policyNoSY) {
        this.policyNoSY = policyNoSY;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getDepartNo() {
        return departNo;
    }

    public void setDepartNo(String departNo) {
        this.departNo = departNo;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    @Override
    public String toString() {
        return "Order{" +
                "policyNoJQ='" + policyNoJQ + '\'' +
                ", policyNoSY='" + policyNoSY + '\'' +
                ", policyNo='" + policyNo + '\'' +
                ", departNo='" + departNo + '\'' +
                ", premium=" + premium +
                '}';
    }
}
