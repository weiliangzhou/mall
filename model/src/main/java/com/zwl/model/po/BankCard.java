package com.zwl.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class BankCard {
    private Integer id;

    /**
     * 银行卡 用户名
     */
    private String bankName;

    /**
     * 银行卡号
     */
    private String bankNo;

    /**
     * 银行卡 省
     */
    private String bankProvince;

    /**
     * 银行卡 市
     */
    private String bankCity;

    /**
     * 银行卡 区
     */
    private String bankArea;
    /**
     * 银行卡 支行
     */
    private String bankBranch;

    /**
     * 所属用户
     */
    private String userId;

    private Date createTime;

    private Date modifyTime;

}