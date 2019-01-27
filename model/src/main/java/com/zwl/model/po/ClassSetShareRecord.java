package com.zwl.model.po;

import lombok.Data;

import java.util.Date;
@Data
public class ClassSetShareRecord {
    private Long id;

    private Long classSetId;

    private String shareUserId;

    private Date createTime;

    private Date modifyTime;

    private Integer available = 1;

}