package cn.com.chinalife.ecdata.entity.user;

/**
 * Created by xiexiangyu on 2018/6/27.
 */
public class UserAttribute {
    private String statTime;
    private String statTimeSpan;
    private String indexName;
    private String indexSource;
    private String distributeType;
    private String category;
    private Integer categoryNum;

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

    public String getIndexSource() {
        return indexSource;
    }

    public void setIndexSource(String indexSource) {
        this.indexSource = indexSource;
    }

    public String getDistributeType() {
        return distributeType;
    }

    public void setDistributeType(String distributeType) {
        this.distributeType = distributeType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCategoryNum() {
        return categoryNum;
    }

    public void setCategoryNum(Integer categoryNum) {
        this.categoryNum = categoryNum;
    }

    @Override
    public String toString() {
        return "UserAttribute{" +
                "statTime='" + statTime + '\'' +
                ", statTimeSpan='" + statTimeSpan + '\'' +
                ", indexName='" + indexName + '\'' +
                ", indexSource='" + indexSource + '\'' +
                ", distributeType='" + distributeType + '\'' +
                ", category='" + category + '\'' +
                ", categoryNum='" + categoryNum + '\'' +
                '}';
    }
}
