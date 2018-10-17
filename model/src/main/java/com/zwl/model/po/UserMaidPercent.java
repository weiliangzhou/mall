package com.zwl.model.po;

import java.util.Date;

public class UserMaidPercent {
    private Integer id;

    private Integer memberLevel;

    private Integer maidPercent1;

    private Integer maidPercent4;

    private Integer maidPercent6;

    private String merchantId;

    private Date createTime;

    private Date modifyTime;

    private Integer available;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(Integer memberLevel) {
        this.memberLevel = memberLevel;
    }

    public Integer getMaidPercent1() {
        return maidPercent1;
    }

    public void setMaidPercent1(Integer maidPercent1) {
        this.maidPercent1 = maidPercent1;
    }

    public Integer getMaidPercent4() {
        return maidPercent4;
    }

    public void setMaidPercent4(Integer maidPercent4) {
        this.maidPercent4 = maidPercent4;
    }

    public Integer getMaidPercent6() {
        return maidPercent6;
    }

    public void setMaidPercent6(Integer maidPercent6) {
        this.maidPercent6 = maidPercent6;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
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