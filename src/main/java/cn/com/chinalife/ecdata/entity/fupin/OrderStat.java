package cn.com.chinalife.ecdata.entity.fupin;

import java.math.BigDecimal;

/**
 * Created by xiexiangyu on 2018/10/24.
 */
public class OrderStat {
    private String statDate;
    private String productId;
    private String productName;
    private Integer goodsNum;
    private Integer orderNum;
    private Integer orderNum1;
    private Integer orderNum2;
    private Integer orderNum3;
    private Integer orderNum4;
    private Integer orderNum5;
    private Integer orderNum6;
    private Integer orderNum7;
    private BigDecimal orderAmount;
    private BigDecimal orderAverage;

    public String getStatDate() {
        return statDate;
    }

    public void setStatDate(String statDate) {
        this.statDate = statDate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum1() {
        return orderNum1;
    }

    public void setOrderNum1(Integer orderNum1) {
        this.orderNum1 = orderNum1;
    }

    public Integer getOrderNum2() {
        return orderNum2;
    }

    public void setOrderNum2(Integer orderNum2) {
        this.orderNum2 = orderNum2;
    }

    public Integer getOrderNum3() {
        return orderNum3;
    }

    public void setOrderNum3(Integer orderNum3) {
        this.orderNum3 = orderNum3;
    }

    public Integer getOrderNum4() {
        return orderNum4;
    }

    public void setOrderNum4(Integer orderNum4) {
        this.orderNum4 = orderNum4;
    }

    public Integer getOrderNum5() {
        return orderNum5;
    }

    public void setOrderNum5(Integer orderNum5) {
        this.orderNum5 = orderNum5;
    }

    public Integer getOrderNum6() {
        return orderNum6;
    }

    public void setOrderNum6(Integer orderNum6) {
        this.orderNum6 = orderNum6;
    }

    public Integer getOrderNum7() {
        return orderNum7;
    }

    public void setOrderNum7(Integer orderNum7) {
        this.orderNum7 = orderNum7;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getOrderAverage() {
        return orderAverage;
    }

    public void setOrderAverage(BigDecimal orderAverage) {
        this.orderAverage = orderAverage;
    }

    @Override
    public String toString() {
        return "OrderStat{" +
                "statDate='" + statDate + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", goodsNum=" + goodsNum +
                ", orderNum=" + orderNum +
                ", orderNum1=" + orderNum1 +
                ", orderNum2=" + orderNum2 +
                ", orderNum3=" + orderNum3 +
                ", orderNum4=" + orderNum4 +
                ", orderNum5=" + orderNum5 +
                ", orderNum6=" + orderNum6 +
                ", orderNum7=" + orderNum7 +
                ", orderAmount=" + orderAmount +
                ", orderAverage=" + orderAverage +
                '}';
    }
}
