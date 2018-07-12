package com.zwl.model;

import lombok.Data;

import java.util.Date;
@Data
public class OrderFlow {
    private Long id;
    private String orderNo;
    private Integer orderStatus;
    private Integer actualMoney;
    private Integer money;
    private String paymentNo;
    private Date paymentTime;
    private Date createTime;
}