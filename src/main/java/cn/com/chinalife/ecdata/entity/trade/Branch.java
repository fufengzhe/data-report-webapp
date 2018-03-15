package cn.com.chinalife.ecdata.entity.trade;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
public class Branch {
    private String branchNo;
    private String branchName;

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "branchNo='" + branchNo + '\'' +
                ", branchName='" + branchName + '\'' +
                '}';
    }
}
