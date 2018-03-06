package cn.com.chinalife.ecdata.entity;

/**
 * Created by xiexiangyu on 2018/3/2.
 */
public class ResponseBean {
    private int respCode = 0;
    private String respMsg = "查询成功!";
    private Object detailInfo;

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public Object getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(Object detailInfo) {
        this.detailInfo = detailInfo;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "respCode=" + respCode +
                ", respMsg='" + respMsg + '\'' +
                ", detailInfo=" + detailInfo +
                '}';
    }
}
