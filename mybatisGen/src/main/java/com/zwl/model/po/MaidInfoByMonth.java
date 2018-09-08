package com.zwl.model.po;

import java.util.Date;

public class MaidInfoByMonth {
    private Long id;

    private String userId;

    private Integer maidMoney;

    private Integer maidPercent;

    private Integer totalPerformance;

    private Integer maidType;

    private String merchantId;

    private Date recordTime;

    private Date createTime;

    private Date modifyTime;

    private Integer available;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Integer getMaidMoney() {
        return maidMoney;
    }

    public void setMaidMoney(Integer maidMoney) {
        this.maidMoney = maidMoney;
    }

    public Integer getMaidPercent() {
        return maidPercent;
    }

    public void setMaidPercent(Integer maidPercent) {
        this.maidPercent = maidPercent;
    }

    public Integer getTotalPerformance() {
        return totalPerformance;
    }

    public void setTotalPerformance(Integer totalPerformance) {
        this.totalPerformance = totalPerformance;
    }

    public Integer getMaidType() {
        return maidType;
    }

    public void setMaidType(Integer maidType) {
        this.maidType = maidType;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}