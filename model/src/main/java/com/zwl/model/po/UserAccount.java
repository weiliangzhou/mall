package com.zwl.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class UserAccount {
    private Long id;

    private String userId;

    private Double balance;

    private Date createTime;

    private Integer available = 1;

    private Date modifyTime;


}