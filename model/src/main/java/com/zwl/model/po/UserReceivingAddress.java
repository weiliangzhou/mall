package com.zwl.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class UserReceivingAddress {
    private Integer id;

    private String receivingName;

    private String province;

    private String city;

    private String area;

    private String address;

    private Integer isDefault;

    private String userId;

    private String merchantId;

    private Date createTime;

    private Date modifyTime;

    private Integer available;
}