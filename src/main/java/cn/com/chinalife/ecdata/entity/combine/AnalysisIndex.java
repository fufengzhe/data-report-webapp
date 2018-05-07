package cn.com.chinalife.ecdata.entity.combine;

/**
 * Created by xiexiangyu on 2018/4/20.
 */
public class AnalysisIndex {
    private int registerNum;
    private int activeNum;
    private String indexSource;
    private String statDate;

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

    public String getIndexSource() {
        return indexSource;
    }

    public void setIndexSource(String indexSource) {
        this.indexSource = indexSource;
    }

    public String getStatDate() {
        return statDate;
    }

    public void setStatDate(String statDate) {
        this.statDate = statDate;
    }

    @Override
    public String toString() {
        return "AnalysisIndex{" +
                "registerNum=" + registerNum +
                ", activeNum=" + activeNum +
                ", indexSource='" + indexSource + '\'' +
                ", statDate='" + statDate + '\'' +
                '}';
    }
}
