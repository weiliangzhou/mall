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
    private Integer registerFrom;
    private String realName;
    private String referrer;
    private Integer memberLevel;
    private String levelName;
    private Date expiresTime;
    private Date registerTime;
    private Date modifyTime;
    private Integer available;
}