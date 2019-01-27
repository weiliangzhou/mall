package com.zwl.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserT {
    private String userId;
    private Integer memberLevel;
    private String levelName;
    private String phone;
    private Integer daozhangmoney;
    private Integer dongjiemoney;
    private Integer money;
    private String zhishu;
    private String zhishuPhone;
    private String zhishuName;
    private String zhishuLevelName;
    private String realName;
}