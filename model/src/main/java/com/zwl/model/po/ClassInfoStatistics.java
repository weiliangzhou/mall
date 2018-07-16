package com.zwl.model.po;

import java.util.Date;

public class ClassInfoStatistics {
    private Integer id;

    private Integer classInfoId;

    private Long listenCount;

    private Date createTime;

    private Date modifyTime;

    private Integer available;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassInfoId() {
        return classInfoId;
    }

    public void setClassInfoId(Integer classInfoId) {
        this.classInfoId = classInfoId;
    }

    public Long getListenCount() {
        return listenCount;
    }

    public void setListenCount(Long listenCount) {
        this.listenCount = listenCount;
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