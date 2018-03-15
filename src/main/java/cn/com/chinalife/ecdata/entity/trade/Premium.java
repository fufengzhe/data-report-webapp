package cn.com.chinalife.ecdata.entity.trade;

import java.math.BigDecimal;

/**
 * Created by xiexiangyu on 2018/3/14.
 */
public class Premium {
    private String statDay;
    private BigDecimal dayAmount;
    private BigDecimal lastDayAmount;
    private String dayRatio;
    private BigDecimal monthAmount;
    private BigDecimal lastMonthAmount;
    private String monthRatio;
    private BigDecimal yearAmount;
    private BigDecimal yearGoal;
    private String completeRatio;
    private BigDecimal accumulatedAmount;
    private String branchName;

    public String getStatDay() {
        return statDay;
    }

    public void setStatDay(String statDay) {
        this.statDay = statDay;
    }

    public BigDecimal getDayAmount() {
        return dayAmount;
    }

    public void setDayAmount(BigDecimal dayAmount) {
        this.dayAmount = dayAmount;
    }

    public BigDecimal getLastDayAmount() {
        return lastDayAmount;
    }

    public void setLastDayAmount(BigDecimal lastDayAmount) {
        this.lastDayAmount = lastDayAmount;
    }

    public String getDayRatio() {
        return dayRatio;
    }

    public void setDayRatio(String dayRatio) {
        this.dayRatio = dayRatio;
    }

    public BigDecimal getMonthAmount() {
        return monthAmount;
    }

    public void setMonthAmount(BigDecimal monthAmount) {
        this.monthAmount = monthAmount;
    }

    public BigDecimal getLastMonthAmount() {
        return lastMonthAmount;
    }

    public void setLastMonthAmount(BigDecimal lastMonthAmount) {
        this.lastMonthAmount = lastMonthAmount;
    }

    public String getMonthRatio() {
        return monthRatio;
    }

    public void setMonthRatio(String monthRatio) {
        this.monthRatio = monthRatio;
    }

    public BigDecimal getYearAmount() {
        return yearAmount;
    }

    public void setYearAmount(BigDecimal yearAmount) {
        this.yearAmount = yearAmount;
    }

    public BigDecimal getYearGoal() {
        return yearGoal;
    }

    public void setYearGoal(BigDecimal yearGoal) {
        this.yearGoal = yearGoal;
    }

    public String getCompleteRatio() {
        return completeRatio;
    }

    public void setCompleteRatio(String completeRatio) {
        this.completeRatio = completeRatio;
    }

    public BigDecimal getAccumulatedAmount() {
        return accumulatedAmount;
    }

    public void setAccumulatedAmount(BigDecimal accumulatedAmount) {
        this.accumulatedAmount = accumulatedAmount;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }


}
