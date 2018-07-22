package com.zwl.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class WithdrawFlow {
    private Long id;
    private String withdrawId;
    private Date successTime;
    private Date paymentTime;
    private Integer paymentNo;
    //0审核中 1审核通过 2未通过 3到款中 4成功
    private Integer status;
    private String operator;
    private String merchantId;
    private Date createTime;
    private Integer available = 1;


}