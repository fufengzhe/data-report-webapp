package cn.com.chinalife.ecdata.entity;

/**
 * Created by xiexiangyu on 2018/10/18.
 */
public class TableMerge {
    private Integer index;
    private String field;
    private Integer colspan;
    private Integer rowspan;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getColspan() {
        return colspan;
    }

    public void setColspan(Integer colspan) {
        this.colspan = colspan;
    }

    public Integer getRowspan() {
        return rowspan;
    }

    public void setRowspan(Integer rowspan) {
        this.rowspan = rowspan;
    }

    @Override
    public String toString() {
        return "TableMerge{" +
                "index=" + index +
                ", field='" + field + '\'' +
                ", colspan=" + colspan +
                ", rowspan=" + rowspan +
                '}';
    }
}
