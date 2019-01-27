package com.zwl.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class TeacherAdmin {
    private String id;

    private String userName;

    private String password;

    private String merchantId;

    private Date registerTime;

    private Date modifyTime;

    private Integer available = 1;


}