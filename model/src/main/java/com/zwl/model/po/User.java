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
    // 99校长   6院长 5班长 4学员 1小班 0会员 -1游客
    private Integer memberLevel = -1;
    private String levelName;
    private Date expiresTime;
    private Date registerTime;
    private Date modifyTime;
    private Integer available = 1;
    //是否购买
    private Integer isBuy;
}