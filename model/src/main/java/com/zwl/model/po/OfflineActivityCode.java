package com.zwl.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class OfflineActivityCode {
    private Integer id;

    private String userId;

    private String activityCode;

    private Integer isUsed;

    private Date createTime = new Date();

    private Date modifyTime;

    private Integer available = 1;

    private Integer activityId;

}