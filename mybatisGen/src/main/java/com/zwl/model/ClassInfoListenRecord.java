package com.zwl.model;

import java.util.Date;

public class ClassInfoListenRecord {
    private Long id;

    private Long classInfoId;

    private String listenUserId;

    private Date createTime;

    private Date modifyTime;

    private Integer available;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClassInfoId() {
        return classInfoId;
    }

    public void setClassInfoId(Long classInfoId) {
        this.classInfoId = classInfoId;
    }

    public String getListenUserId() {
        return listenUserId;
    }

    public void setListenUserId(String listenUserId) {
        this.listenUserId = listenUserId == null ? null : listenUserId.trim();
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