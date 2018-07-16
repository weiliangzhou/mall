package com.zwl.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class UserQuotaCount {
    private String id;

    private Integer count;

    private Integer type;

    private Date createTime;

    private Date modifyTime;

    private Integer available;


}