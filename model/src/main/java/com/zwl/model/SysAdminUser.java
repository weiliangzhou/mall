package com.zwl.model;

import lombok.Data;

import java.util.Date;
@Data
public class SysAdminUser {
    private Long id;

    private String nickname;

    private String email;

    private String pswd;

    private Date createTime;

    private Date lastLoginTime;

    private Long status;

}