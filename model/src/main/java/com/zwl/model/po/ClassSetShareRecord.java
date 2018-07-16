package com.zwl.model.po;

import java.util.Date;

public class ClassSetShareRecord {
    private Long id;

    private Integer classSetId;

    private Integer shareUserId;

    private Date createTime;

    private Date modifyTime;

    private Integer available;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getClassSetId() {
        return classSetId;
    }

    public void setClassSetId(Integer classSetId) {
        this.classSetId = classSetId;
    }

    public Integer getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(Integer shareUserId) {
        this.shareUserId = shareUserId;
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