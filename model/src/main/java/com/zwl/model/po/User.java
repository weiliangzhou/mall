package com.zwl.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long id;
    private String userId;
    private String wechatOpenid;
    private String wechatUnionId;
    private String merchantId;
    //    从什么渠道注册。1、wechat 微信注册 2、线下导入
    private Integer registerFrom;
    private String realName;
    private String logoUrl;
    private String registerMobile;
    private String referrer;
    private Integer memberLevel;
    private String levelName;
    private Date expiresTime;
    private Date registerTime;
    private Date modifyTime;
    private Integer available = 1;
    //是否购买
    private Integer isBuy;
}