package cn.com.chinalife.ecdata.entity;

/**
 * Created by xiexiangyu on 2018/8/29.
 */
public class UpdateResult {
    private String statTime;
    private String statTimeSpan;
    private String indexName;
    private String indexDesc;
    private Integer effectedRowNum;

    public String getStatTime() {
        return statTime;
    }

    public void setStatTime(String statTime) {
        this.statTime = statTime;
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

    public String getIndexDesc() {
        return indexDesc;
    }

    public void setIndexDesc(String indexDesc) {
        this.indexDesc = indexDesc;
    }

    public Integer getEffectedRowNum() {
        return effectedRowNum;
    }

    public void setEffectedRowNum(Integer effectedRowNum) {
        this.effectedRowNum = effectedRowNum;
    }

    @Override
    public String toString() {
        return "UpdateResult{" +
                "statTime='" + statTime + '\'' +
                ", statTimeSpan='" + statTimeSpan + '\'' +
                ", indexName='" + indexName + '\'' +
                ", indexDesc='" + indexDesc + '\'' +
                ", effectedRowNum=" + effectedRowNum +
                '}';
    }
}
