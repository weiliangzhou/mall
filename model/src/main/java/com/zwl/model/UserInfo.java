package com.zwl.model;

import lombok.Data;

import java.util.Date;
@Data
public class UserInfo {
    private Long id;

    private String userId;

    private String userName;

    private String nickName;

    private String wechatMobile;

    private String referrer;

    private String logoUrl;

    private Integer memberLevel;

    private Integer isCertification;

    private Integer isBindMobile;

    private String registerMobile;

    private Date modifyTime;

    private Date registerTime;

    private Integer available;
}