package com.zwl.model.po;

import lombok.Data;

import java.util.Date;

@Data
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


}