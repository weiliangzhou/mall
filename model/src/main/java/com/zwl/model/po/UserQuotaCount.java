package com.zwl.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class UserQuotaCount {
    private String id;
    private Integer count;
    //总共可邀请小班名额
    private Integer totalCount;
    private Integer type;
    private Date createTime=new Date();
    private Date modifyTime;
    private Integer available = 1;


}