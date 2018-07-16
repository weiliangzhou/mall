package com.zwl.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class Merchant {
    private String merchantId;

    private String merchantName;

    private String creator;

    private String appId;

    private String appSecret;

    private Date createTime;

    private Date modifyTime;

    private Integer available;

}