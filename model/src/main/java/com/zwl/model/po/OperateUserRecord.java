package com.zwl.model.po;

import lombok.Data;

import java.util.Date;

/**
 * 后台操作用户 记录表
 */
@Data
public class OperateUserRecord {
    private Long id;

    private String operator;

    private String userId;

    private String merchantId;

    private Integer beforeLevel;

    private Integer afterLevel;

    private Date createTime;

    private Date modifyTime;

    private Integer available;
}