package cn.com.chinalife.ecdata.entity.salesman;

/**
 * Created by xiexiangyu on 2018/4/20.
 */
public class SalesmanRelated {
    private String accountCode;
    private String oldUserId;
    private String userName;
    private String identifyNo;
    private String mobile;
    private String bankAccount;
    private String bankName;

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getOldUserId() {
        return oldUserId;
    }

    public void setOldUserId(String oldUserId) {
        this.oldUserId = oldUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdentifyNo() {
        return identifyNo;
    }

    public void setIdentifyNo(String identifyNo) {
        this.identifyNo = identifyNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public String toString() {
        return "SalesmanRelated{" +
                "accountCode='" + accountCode + '\'' +
                ", oldUserId='" + oldUserId + '\'' +
                ", userName='" + userName + '\'' +
                ", identifyNo='" + identifyNo + '\'' +
                ", mobile='" + mobile + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", bankName='" + bankName + '\'' +
                '}';
    }
}
