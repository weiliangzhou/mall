package com.zwl.model.po;

import lombok.Data;

import java.util.Date;
@Data
public class ClassInfoListenRecord {
    private Long id;

    private Long classInfoId;

    private String listenUserId;

    private Date createTime;

    private Date modifyTime;

    private Integer available = 1;

}