package com.zwl.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class OfflineActivityOrder {
    private String orderNo;

    private Integer activityId;

    private String activityCode;

    private Integer activityPrice;

    private Integer actualMoney;

    private Integer orderStatus;

    private String userId;

    private Integer sex;

    private String phone;

    private String realName;

    private String city;

    private String idCardNum;

    private String paymentNo;

    private Date paymentTime;

    private String merchantId;

    private Date createTime;

    private Date modifyTime;

    private Integer available;

    private Integer isMaid;

    private Integer isRetraining;

    private String qrCodeUrl;

}